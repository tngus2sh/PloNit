self.onmessage = (event) => {
  if (event.data === "start") {
    console.log("worker1");
    setInterval(() => {
      self.postMessage("tick");
    }, 1000);
  }
  if (event.data === "start2") {
    setInterval(() => {
      console.log("worker2");
      self.postMessage("tick2");
    }, 2000);
  }
  if (event.data === "start30") {
    setInterval(() => {
      console.log("worker3");
      self.postMessage("tick30");
    }, 30000);
  }
};
