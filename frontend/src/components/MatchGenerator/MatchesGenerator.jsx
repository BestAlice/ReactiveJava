import React from "react";
import "../../pages/Matches/Matches.css";
import OngoingMatchMaker from "../MatchMacker/OngoingMatchMaker";
import UpcomingMatchMaker from "../MatchMacker/UpcomingMatchMaker";

function MatchesGenerator(props) {
    return (
        <div>
            {props.ongoing_matches !== null && props.ongoing_matches.length > 0 ?
                <div className="matches_header">

                    <div className="row_center_5px">
                        <p>Текущие матчи</p>
                    </div>
                </div>
                :
                <></>
            }
            <div className="matches">
                <div className="col_center_gap10">
                    {
                        props.ongoing_matches !== null ?
                            props.ongoing_matches.map((match) =>
                                <OngoingMatchMaker {...match} key={match.matchId} />
                            )
                            :
                            <></>
                    }
                </div>
            </div>

            {props.upcoming_matches !== null && props.upcoming_matches.length > 0 ?
                <div className="matches_header">
                    <div className="row_center_5px">
                        <p>Ближайшие матчи</p>
                    </div>
                </div>
                :
                <></>
            }
            <div className="matches">
                {
                    props.upcoming_matches !== null ?
                        props.upcoming_matches.map((day) =>
                            <UpcomingMatchMaker {...day} key={day.date} />
                        )
                        :
                        <></>
                }
            </div>
        </div>
    );
}

export default MatchesGenerator;