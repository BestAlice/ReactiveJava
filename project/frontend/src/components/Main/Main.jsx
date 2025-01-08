import { Routes, Route } from 'react-router-dom'
import Matches from '../../pages/Matches/Matches'
import AboutUs from '../../pages/AboutUs/AboutUs'
import Match from '../../pages/Match/Match';
import Subscriptions from '../../pages/Subscriptions/Subscriptions';

function Main() {
    return (
        <main>
            <Routes>
                <Route exact path="/" element={<Matches />} />
                <Route path="/about-us" element={<AboutUs />} />
                <Route path="/match/:id" element={<Match />} />
                <Route path="/subscribes" element={<Subscriptions />} />
            </Routes>
        </main>
    );
}

export default Main;