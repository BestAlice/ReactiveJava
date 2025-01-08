import React, { useState, useEffect } from "react";
import "./Matches.css";
import MatchesGenerator from "../../components/MatchGenerator/MatchesGenerator";
import Preloader from "../../components/Preloader/Preloader";
import { request } from "../../utils/Utils";

function Matches() {
  const [ongoingMatches, setOngoingMatches] = useState(null);
  const [upcomingMatches, setUpcomingMatches] = useState(null);


  async function getFullMatches() {
    const liveMatches = await request("GET", `/match/ongoing`, {});
    setOngoingMatches(liveMatches.data);

    const notLiveMatches = await request("GET", `/match/upcoming`, {});
    setUpcomingMatches(notLiveMatches.data);
  }


  useEffect(() => {
    getFullMatches();
  }, []);


  return (
    <div>
      {ongoingMatches ?
        <MatchesGenerator
          ongoing_matches={ongoingMatches}
          upcoming_matches={upcomingMatches}
        />
        :
        <Preloader />
      }
    </div>
  );
}

export default Matches;