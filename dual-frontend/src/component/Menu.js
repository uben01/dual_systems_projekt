import { Link } from 'react-router-dom';

export function Menu() {
    return (
        <nav id="menu">
            <Link className="btn btn-primary" to='/'>List invoices</Link>
            <Link className="btn btn-primary" to='/create'>Create new invoice</Link>
        </nav>
    );
}