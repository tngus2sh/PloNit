self.onmessage = (event) => {
  if (event.data === "start") {
    setInterval(() => {
      self.postMessage("tick");
    }, 1000);
  }
};
