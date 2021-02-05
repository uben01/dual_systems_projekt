import { Link } from "react-router-dom";
import { precise } from "./utils";

export function ListInvoice(props) {
    return (
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
                    props.invoiceList.map(item => (
                        <tr key={item.id}>
                            <td><Link className="btn btn-success"
                                      to={{
                                          pathname: "/invoice/" + item.id,
                                          props: {invoiceList: props.invoiceList}
                                      }}>
                                {item.customerName}</Link></td>
                            <td>{item.issueDate}</td>
                            <td>{item.dueDate}</td>
                            <td>{item.comment}</td>
                            <td>{item.totalHUF} Ft</td>
                            <td>{precise(item.totalEUR)} €</td>
                        </tr>
                    ))
                }
            </tbody>
        </table>
    );

}