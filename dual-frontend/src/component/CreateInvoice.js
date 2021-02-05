import { Component } from "react";
import Modal from 'react-modal';
import CreateItem from "./CreateItem";
import { precise } from "./utils";

class CreateInvoice extends Component {

    constructor() {
        super();
        this.state = {
            items: [],
            showModal: false,
            showResult: null,
            lastId: 0,
            exchangeRate: 350, // backup

            customerName: "", validCustomerName: false,
            issueDate: null, validIssueDate: false,
            dueDate: null, validDueDate: false,
            comment: "",

            totalHUF: 0, totalEUR: 0
        };

        this.handleOpenModal = this.handleOpenModal.bind(this);
        this.handleCloseModal = this.handleCloseModal.bind(this);
        this.handleSaveModal = this.handleSaveModal.bind(this);
        this.onSubmitInvoice = this.onSubmitInvoice.bind(this);

        this.onChangeCustomerName = this.onChangeCustomerName.bind(this);
        this.onChangeIssueDate = this.onChangeIssueDate.bind(this);
        this.onChangeDueDate = this.onChangeDueDate.bind(this);
        this.onChangeComment = this.onChangeComment.bind(this);

        this.getTotalPrice = this.getTotalPrice.bind(this);
    }

    componentDidMount() {
        fetch('http://localhost:8080/invoices/exchangeRate')
            .then(response => response.json())
            .then(json => this.setState({exchangeRate: json.rate}));

    }

    handleOpenModal() {
        this.setState({showModal: true});
    }

    handleCloseModal() {
        this.setState({showModal: false});
    }

    handleSaveModal(item) {
        let tempItems = this.state.items;

        item.id = this.state.lastId;
        tempItems.unshift(item);

        this.setState({
            items: tempItems,
            showModal: false,
            lastId: this.state.lastId + 1
        });

        this.getTotalPrice();
    }

    onSubmitInvoice() {
        let jsonPackage = {
            customerName: this.state.customerName,
            issueDate: this.state.issueDate,
            dueDate: this.state.dueDate,
            comment: this.state.comment,
            items: this.state.items
        };

        var xhr = new XMLHttpRequest();
        var url = "http://localhost:8080/invoices";

        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var json = JSON.parse(xhr.responseText);
                this.setState({
                    showResult: json.result
                });
            }
        }.bind(this);
        var data = JSON.stringify(jsonPackage);
        xhr.send(data);
    }

    onChangeCustomerName(customerName) {
        this.setState({
            customerName: customerName,
            validCustomerName: customerName.length > 0
        });
    }

    onChangeIssueDate(issueDate) {
        console.log(issueDate);
        this.setState({
            issueDate: issueDate,
            validIssueDate: issueDate != "",
            validDueDate: issueDate > this.state.dueDate
        });
    }

    onChangeDueDate(dueDate) {
        this.setState({
            dueDate: dueDate,
            validDueDate: dueDate > this.state.issueDate
        });
    }

    onChangeComment(comment) {
        this.setState({
            comment: comment
        });
    }

    getTotalPrice() {
        let totalHUF = 0;
        let totalEUR = 0;
        this.state.items.forEach(item => {
            totalHUF += item.totalPriceHUF;
            totalEUR += item.totalPriceEUR;
        });
        this.setState({
            totalHUF: totalHUF,
            totalEUR: totalEUR
        });
    }

    render() {
        return (
            <div>
                {
                    (!!this.state.showResult ? (
                        <div id="showAlert"
                             className={this.state.showResult === "success" ? "alert alert-success" : "alert alert-danger"}>
                            {this.state.showResult === "success" ? "Creation successful" : "Error while creating element"}
                        </div>
                    ) : "")
                }
                <form>
                    <div>
                        <label>Customer name</label>
                        <input type="text"
                               className={"form-control " + (this.state.validCustomerName ? "is-valid" : "is-invalid")}
                               placeholder="Customer name"
                               onChange={evt => this.onChangeCustomerName(evt.target.value)} />
                    </div>
                    <div>
                        <label>Issue date</label>
                        <input type="date"
                               className={"form-control " + (this.state.validIssueDate ? "is-valid" : "is-invalid")}
                               onChange={evt => this.onChangeIssueDate(evt.target.value)} />
                    </div>
                    <div>
                        <label>Due date</label>
                        <input type="date"
                               className={"form-control " + (this.state.validDueDate ? "is-valid" : "is-invalid")}
                               onChange={evt => this.onChangeDueDate(evt.target.value)} />
                    </div>
                    <div>
                        <label>Comment</label>
                        <textarea className="form-control" onChange={evt => this.onChangeComment(evt.target.value)} />
                    </div>
                    <table className="table table-secondary" id="createInvoiceItemList">
                        <thead>
                            <tr>
                                <th>Product name</th>
                                <th>Unit price</th>
                                <th>Quantity</th>
                                <th>Total (HUF)</th>
                                <th>Total (EUR)</th>
                            </tr>
                        </thead>
                        <tbody>
                            {(this.state.items.length === 0 ?
                                <tr>
                                    <td id="emptyListPlaceholder" colSpan="6">No items registered yet</td>
                                </tr>
                                :
                                this.state.items.map(item => (
                                    <tr key={item.id}>
                                        <td>{item.productName}</td>
                                        <td>{item.unitPrice}</td>
                                        <td>{item.quantity}</td>
                                        <td>{item.totalPriceHUF} Ft</td>
                                        <td>{precise(item.totalPriceEUR)} €</td>
                                    </tr>
                                )))

                            }
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colSpan={3} />
                                <td>{this.state.totalHUF} Ft</td>
                                <td>{precise(this.state.totalEUR)} €</td>
                            </tr>
                        </tfoot>
                    </table>
                    <div className="createButtons">
                        <input
                            disabled={!(this.state.items.length != 0 && this.state.validCustomerName && this.state.validDueDate && this.state.validIssueDate)}
                            className="btn btn-success" type="button"
                            onClick={event => this.onSubmitInvoice(event)}
                            value="Submit!" />
                        <input type="button" className="btn btn-primary" value="Add item"
                               onClick={this.handleOpenModal} />
                    </div>
                </form>
                <Modal
                    appElement={document.getElementById('root')}
                    style={{
                        content: {
                            width: '50%',
                            height: '50%',
                            margin: 'auto'
                        }
                    }} isOpen={this.state.showModal}>
                    <CreateItem exchangeRate={this.state.exchangeRate} handleSave={this.handleSaveModal}
                                handleClose={this.handleCloseModal} />
                </Modal>
            </div>
        );
    }
}

export default CreateInvoice;