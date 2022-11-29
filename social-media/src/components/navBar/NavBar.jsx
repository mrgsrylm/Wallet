import React from 'react'
import { Link } from 'react-router-dom';
import HomeOutlinedIcon from "@mui/icons-material/HomeOutlined";
import DarkModeOutlinedIcon from "@mui/icons-material/DarkModeOutlined";
import WbSunnyOutlinedIcon from "@mui/icons-material/WbSunnyOutlined";
import GridViewOutlinedIcon from "@mui/icons-material/GridViewOutlined";
import SearchOutlinedIcon from "@mui/icons-material/SearchOutlined";
import PersonOutlinedIcon from "@mui/icons-material/PersonOutlined";
import EmailOutlinedIcon from "@mui/icons-material/EmailOutlined";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import { useContext } from "react";
import { DarkModeContext } from "../../contex/darkModeContext";
import "./navBar.scss";

const NavBar = () => {
  const { toggle, darkMode } = useContext(DarkModeContext)

  return (
    <div className='navbar'>
      <div className="left">
        <Link to="/" style={{ textDecoration:"none" }}>
          <span>SocialMedia</span>
        </Link>
        <HomeOutlinedIcon />
        {darkMode ? (
          <WbSunnyOutlinedIcon onClick={toggle} />
        ) : (
          <DarkModeOutlinedIcon onClick={toggle} />
        )}
        <GridViewOutlinedIcon />
        <div className="search">
          <SearchOutlinedIcon />
          <input type="text" placeholder="Search..." />
        </div>
      </div>
      <div className="right">
        <PersonOutlinedIcon />
        <EmailOutlinedIcon />
        <NotificationsOutlinedIcon />
        <div className="user">
          <img src="" alt="" />
          <span>Jhon Doe</span>
        </div>
      </div>
    </div>
  )
}

export default NavBar