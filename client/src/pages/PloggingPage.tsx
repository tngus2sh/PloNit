import React from "react";
import DefaultMap from "components/plogging/DefaultMap";
import PathMap from "components/plogging/PathMap";

const PloggingPage = () => {
  return (
    <div>
      <DefaultMap subHeight={56} />
      {/* <PathMap subHeight={56}></PathMap> */}
    </div>
  );
};

export default PloggingPage;
