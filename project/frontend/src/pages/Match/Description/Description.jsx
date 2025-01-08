import React from "react";
import "./Description.css"

function Description(props) {

    return (
        <div>
            <div className="row_center_5px" style={{ marginBottom: "5px" }}>
                <p className="p_fixer">Карты</p>
            </div>
            <div className="match_info_upcoming_maps_desc">
                <p>{props.match.matchInfo}</p>
            </div>
        </div>
    )
}

export default Description;