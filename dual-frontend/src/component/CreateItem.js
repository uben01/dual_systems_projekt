import { Component } from "react";
import { formatEUR, formatHUF } from "./utils";

class CreateItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            productName: "", validProductName: false,
            unitPrice: 0, validUnitPrice: false,
            quantity: 0, validQuantity: false,
            totalPriceHUF: 0,
            totalPriceEUR: 0
        };

        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeUnitPrice = this.onChangeUnitPrice.bind(this);
        this.onChangeQuantity = this.onChangeQuantity.bind(this);
    }

    onChangeName(name) {
        this.setState({
            productName: name,
            validProductName: name.length > 0
        });
    }

    onChangeUnitPrice(unitPrice) {
        this.setState({
            unitPrice: unitPrice,
            totalPriceHUF: unitPrice * this.state.quantity,
            totalPriceEUR: (unitPrice * this.state.quantity) / this.props.exchangeRate,

            validUnitPrice: unitPrice > 0,
        });
    }

    onChangeQuantity(quantity) {
        this.setState({
            quantity: quantity,
            totalPriceHUF: this.state.unitPrice * quantity,
            totalPriceEUR: (this.state.unitPrice * quantity) / this.props.exchangeRate,

            validQuantity: quantity > 0
        });
    }

    render() {
        return (
            <form>
                <div>
                    <label>Product name</label>
                    <input itemID="itemName" type="text" className={"form-control " + (this.state.validProductName ? "is-valid" : "is-invalid")} placeholder="Product name"
                           onChange={evt => this.onChangeName(evt.target.value)} />
                </div>
                <div>
                    <label>Unit price (HUF)</label>
                    <input type="number" min="0" className={"form-control " + (this.state.validUnitPrice ? "is-valid" : "is-invalid")} onChange={evt => this.onChangeUnitPrice(evt.target.value)} />
                </div>
                <div>
                    <label>Quantity</label>
                    <input type="number" min="0" className={"form-control " + (this.state.validQuantity ? "is-valid" : "is-invalid")} onChange={evt => this.onChangeQuantity(evt.target.value)} />
                </div>
                <div>
                    <label>Total (HUF)</label>
                    <div className="form-control" >{formatHUF(this.state.totalPriceHUF)}</div>
                </div>
                <div>
                    <label>Total (EUR)</label>
                    <div className="form-control" >{formatEUR(this.state.totalPriceEUR)}</div>
                </div>
                <div className="createButtons">
                    <input className="btn btn-success" type="submit" value="Add"
                           disabled={!(this.state.validProductName && this.state.validQuantity && this.state.validUnitPrice)}
                           onClick={_ => this.props.handleSave(this.state)} />
                    <input className="btn btn-danger" type="button" value="Close" onClick={this.props.handleClose} />
                </div>
            </form>
        );
    }
}

export default CreateItem;