self.onmessage = (event) => {
  if (event.data === "start") {
    setInterval(() => {
      self.postMessage("tick");
    }, 1000);
  }
  if (event.data === "start2") {
    setInterval(() => {
      self.postMessage("tick2");
    }, 2000);
  }
  if (event.data === "start30") {
    setInterval(() => {
      self.postMessage("tick30");
    }, 30000);
  }
};
