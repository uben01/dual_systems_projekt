import '../css/App.css';

import { BrowserRouter as Router, Route } from "react-router-dom";
import { ListInvoice } from "./ListInvoice";
import { Invoice } from "./Invoice";
import { Menu } from "./Menu";
import { Switch } from "react-router";
import { Component } from "react/cjs/react.production.min";
import CreateInvoice from "./CreateInvoice";

class App extends Component {
    constructor() {
        super();
        this.state = {invoices: []};
    }

    componentDidMount() {
        fetch('http://localhost:8080/invoices')
            .then(response => response.json())
            .then(json => this.setState({invoices: json}));
    }

    render() {
        return (
            <>
                <Router>
                    <Menu />
                    <Switch>
                        <Route path="/invoice/:id"><Invoice invoiceList={this.state.invoices} /></Route>
                        <Route path="/create"><CreateInvoice /></Route>
                        <Route path="/"><ListInvoice invoiceList={this.state.invoices} /></Route>
                    </Switch>

                </Router>
            </>
        );
    }
}

export default App;
