import React from "react";
import "./AboutUs.css";
import AboutUsCard from "../../components/AboutUsComponents/AboutUsCard/AboutUsCard";

function AboutUs() {
    return (
        <div className="about-us-main">
            <div className="col_center_gap5">
                <span className="about-us-label">Команда создателей</span>
                <div className="about-us-cards-wrapper display-row-center">
                    <AboutUsCard src="about_us/Kirill.png" name="Кирилл Симовин">
                        <span className="about-us-info">
                            Fullstack-разработчик,<br />
                            студент группы P4116
                        </span>
                    </AboutUsCard>
                    <AboutUsCard src="about_us/Dmitiry.png" name="Дмитрий Голоскок">
                        <span className="about-us-info">
                            Frontend-разработчик,<br />
                            студент группы P4119
                        </span>
                    </AboutUsCard>
                </div>
            </div>
        </div>
    )
}

export default AboutUs;