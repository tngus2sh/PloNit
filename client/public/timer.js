let interval1;
let interval2;
let interval3;

self.onmessage = (event) => {
  if (event.data === "start") {
    interval1 = setInterval(() => {
      console.log("tick1");
      self.postMessage("tick");
    }, 1000);
  }
  if (event.data === "start2") {
    interval2 = setInterval(() => {
      self.postMessage("tick2");
    }, 2000);
  }
  if (event.data === "start3") {
    interval3 = setInterval(() => {
      self.postMessage("tick3");
    }, 30000);
  }
  if (event.data === "end1") {
    clearInterval(interval1);
  }
  if (event.data === "end2") {
    clearInterval(interval2);
  }
  if (event.data === "end3") {
    clearInterval(interval3);
  }
};
