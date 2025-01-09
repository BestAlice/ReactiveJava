import React, { useState, useEffect } from "react";
import "./Subscriptions.css";
import { request } from "../../utils/Utils";
import SubscribeMatch from "../../components/SubscribeMatch/SubscribeMatch";
import Preloader from "../../components/Preloader/Preloader";

function Subscriptions() {
    const [subscribedMatches, setSubscribedMatches] = useState(null);

    async function getSubscribedMatches() {
        let matches = await request("GET", `match/subscriptions`);
        setSubscribedMatches(matches.data);
    }

    useEffect(() => {
        getSubscribedMatches();
    }, []);

    return (
        <div className="subscriptions">
            {
                subscribedMatches !== null ?
                    subscribedMatches.map((match) =>
                        <SubscribeMatch match={match} key={match.id} />
                    )
                    :
                    <Preloader />
            }
        </div>
    );
}
export default Subscriptions;