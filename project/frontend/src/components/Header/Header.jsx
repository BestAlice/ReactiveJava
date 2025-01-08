import { Link, NavLink } from 'react-router-dom';
import './Header.css';


function Header() {
    return (
        <header className='Header'>
            <div className='Header-content'>
                <Link to={"/"}>
                    <div className="Header-logo"></div>
                </Link>
                <nav className='Navigation'>
                    <ul className='Navigation-list'>
                        <li className='Navigation-link' key="Header Матчи">
                            <NavLink to='/' style={({ isActive }) => ({
                                color: isActive ? 'var(--text-01)' : 'var(--text-02)'
                            })}>
                                Матчи
                            </NavLink>
                        </li>
                        <li className='Navigation-link' key="Header Подписки">
                            <NavLink to='/subscribes' style={({ isActive }) => ({
                                color: isActive ? 'var(--text-01)' : 'var(--text-02)'
                            })}>
                                Подписки
                            </NavLink>
                        </li>
                    </ul>
                </nav>
            </div>
        </header >
    )
};

export default Header;