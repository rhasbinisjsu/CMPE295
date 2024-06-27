import React, { useState, useEffect } from "react";

import Sidebar from "./Sidebar";
import UserDashboard from "./UserDashboard";
function Mission() {
  const [data, setData] = useState(null);

  return (
    <div className="flex">
      <Sidebar />
      <div className="flex-1 p-10">
          <h1 className="text-3xl font-bold mb-6">Mission</h1>
      </div>
    </div>
  );
}

export default Mission;