import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import BottomNavigation from "@mui/material/BottomNavigation";
import BottomNavigationAction from "@mui/material/BottomNavigationAction";
import HomeIcon from "@mui/icons-material/Home";
import ListAltRounded from "@mui/icons-material/ListAltRounded";
import Person from "@mui/icons-material/Person";
import FavoriteIcon from "@mui/icons-material/Favorite";
import LocationOnIcon from "@mui/icons-material/LocationOn";

export default function LabelBottomNavigation() {
  const navigate = useNavigate();

  const location = useLocation();
  const [value, setValue] = React.useState("home");

  const handleChange = (event: React.SyntheticEvent, newValue: string) => {
    setValue(newValue);
  };

  useEffect(() => {
    switch (location.pathname) {
      case "/":
        setValue("home");
        break;
      case "/crew/list":
        setValue("crews");
        break;
      case "/plogging":
        setValue("plogging");
        break;
      case "/ranking":
        setValue("ranking");
        break;
      case "/profile":
        setValue("profile");
        break;
      default:
        setValue("");
    }
  }, [location.pathname]);

  return (
    <BottomNavigation value={value} onChange={handleChange}>
      <BottomNavigationAction
        style={{ color: value === "home" ? "#2CD261" : "gray" }}
        label="Home"
        value="home"
        icon={<HomeIcon />}
        sx={{ minWidth: "70px" }}
        onClick={() => {
          navigate("/");
        }}
      />
      <BottomNavigationAction
        style={{ color: value === "crews" ? "#2CD261" : "gray" }}
        label="Crews"
        value="crews"
        icon={<ListAltRounded />}
        sx={{ minWidth: "70px" }}
        onClick={() => {
          navigate("/crew/list");
        }}
      />
      <BottomNavigationAction
        style={{ color: value === "plogging" ? "#2CD261" : "gray" }}
        label="Plogging"
        value="plogging"
        icon={<FavoriteIcon />}
        sx={{ minWidth: "70px" }}
        onClick={() => {
          navigate("/plogging");
        }}
      />
      <BottomNavigationAction
        style={{ color: value === "ranking" ? "#2CD261" : "gray" }}
        label="Ranking"
        value="ranking"
        icon={<LocationOnIcon />}
        sx={{ minWidth: "70px" }}
        onClick={() => {
          navigate("/ranking");
        }}
      />
      <BottomNavigationAction
        style={{ color: value === "profile" ? "#2CD261" : "gray" }}
        label="Profile"
        value="profile"
        icon={<Person />}
        sx={{ minWidth: "70px" }}
        onClick={() => {
          navigate("/profile");
        }}
      />
    </BottomNavigation>
  );
}
