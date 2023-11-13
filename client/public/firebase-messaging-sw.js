self.addEventListener("push", function (e) {
  console.log("push: ", e.data.json());
  if (!e.data.json()) return;

  const resultData = e.data.json().notification;
  const notificationTitle = resultData.title;
  const notificationOptions = {
    body: resultData.body,
    ...resultData,
  };
  console.log("push: ", { resultData, notificationTitle, notificationOptions });

  registration.showNotification(notificationTitle, notificationOptions);
});
