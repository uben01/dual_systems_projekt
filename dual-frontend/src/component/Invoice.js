import { useParams } from "react-router";
import { Link } from "react-router-dom";
import { precise } from "./utils";

export function Invoice(props) {
    let {id} = useParams();

    const invoices = props.invoiceList;
    let invoice = {
        id: null,
        customerName: null,
        issueDate: null,
        dueDate: null,
        comment: null,
        totalHUF: null,
        totalEUR: null,
        items: []
    };

    invoices.forEach(item => {
        // eslint-disable-next-line eqeqeq
        if (item.id == id) {
            invoice = item;
        }
    });

    return (
        <>
            <table id="invoiceTable" className="table-warning table">
                <thead>
                    <tr>
                        <th>Customer Name</th>
                        <th>Issue Date</th>
                        <th>Due Date</th>
                        <th>Comment</th>
                        <th>Invoice total (HUF)</th>
                        <th>Invoice total (EUR)</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        <tr key={invoice.id}>
                            <td><Link className="btn btn-success"
                                      to={{
                                          pathname: "/invoice/" + invoice.id,
                                          props: {invoiceList: props.invoiceList}
                                      }}>
                                {invoice.customerName}</Link></td>
                            <td>{invoice.issueDate}</td>
                            <td>{invoice.dueDate}</td>
                            <td>{invoice.comment}</td>
                            <td>{invoice.totalHUF} Ft</td>
                            <td>{precise(invoice.totalEUR)} €</td>
                        </tr>

                    }
                </tbody>
            </table>
            <table className="table table-secondary">
                <thead>
                    <tr>
                        <th>Product name</th>
                        <th>Unit price (HUF)</th>
                        <th>Quantity</th>
                        <th>Total (HUF)</th>
                        <th>Total (EUR)</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        invoice.items.map(item => (
                            <tr key={item.id}>
                                <td>{item.productName}</td>
                                <td>{item.unitPrice} Ft</td>
                                <td>{item.quantity}</td>
                                <td>{item.totalHUFPrice} Ft</td>
                                <td>{precise(item.totalEURPrice)} €</td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>
        </>
    );

}