import { Routes, Route } from 'react-router-dom'
import Matches from '../../pages/Matches/Matches'
import AboutUs from '../../pages/AboutUs/AboutUs'

function Main() {
    return (
        <main>
            <Routes>
                <Route exact path="/" element={<Matches />} />
                <Route path="/about-us" element={<AboutUs />} />
            </Routes>
        </main>
    );
}

export default Main;