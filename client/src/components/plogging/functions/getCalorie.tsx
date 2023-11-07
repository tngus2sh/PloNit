function getCalorie({
  second,
  distance,
  kg,
}: {
  second: number;
  distance: number;
  kg: number;
}) {
  const MET = distance === 0 ? 0 : 0.2 * (distance / second) + 1.8;

  return (MET * kg * second) / 3600;
}

export default getCalorie;
