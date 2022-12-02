import { useContext } from "react";
import { AuthContext } from "../../contex/authContext";
import "./stories.scss";

const Stories = () => {
  const { currentUser } = useContext(AuthContext);
  const stories = [
    {
      id: 1,
      name: "Jhon Doe",
      img: "https://robohash.org/hicveldicta.png",
    },
    {
      id: 2,
      name: "Jhon Doe",
      img: "https://robohash.org/hicveldicta.png",
    },
    {
      id: 3,
      name: "Jhon Doe",
      img: "https://robohash.org/hicveldicta.png",
    },
    {
      id: 4,
      name: "Jhon Doe",
      img: "https://robohash.org/hicveldicta.png",
    },
  ];

  return (
    <div className="stories">
      <div className="story">
        <img src={currentUser.profilePic} alt="" />
        <span>{currentUser.name}</span>
        <button>+</button>
      </div>
      {stories.map((story) => (
        <div className="story" key={story.id}>
          <img src={story.img} alt="" />
          <span>{story.name}</span>
        </div>
      ))}
    </div>
  );
};

export default Stories;
