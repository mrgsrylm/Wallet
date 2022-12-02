import "./posts.scss";
import Post from '../post/Post'

const Posts = () => {
  const posts = [
    {
      id: 1,
      name: "Jhon Doe",
      userId: 1,
      profilePic: "https://robohash.org/doloremquesintcorrupti.png",
      desc: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Itaque labore vero ratione molestiae, modi iste saepe dignissimos voluptatum ipsam repellat temporibus qui ducimus reprehenderit nam debitis omnis veritatis enim! Saepe!",
      img: "https://robohash.org/consequunturautconsequatur.png",
    },
    {
      id: 2,
      name: "Jhon Doe",
      userId: 2,
      profilePic: "https://robohash.org/doloremquesintcorrupti.png",
      desc: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Itaque labore vero ratione molestiae, modi iste saepe dignissimos voluptatum ipsam repellat temporibus qui ducimus reprehenderit nam debitis omnis veritatis enim! Saepe!",
      img: "https://robohash.org/consequunturautconsequatur.png",
    },
    {
      id: 3,
      name: "Jhon Doe",
      userId: 3,
      profilePic: "https://robohash.org/doloremquesintcorrupti.png",
      desc: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Itaque labore vero ratione molestiae, modi iste saepe dignissimos voluptatum ipsam repellat temporibus qui ducimus reprehenderit nam debitis omnis veritatis enim! Saepe!",
      img: "https://robohash.org/consequunturautconsequatur.png",
    },
    {
      id: 4,
      name: "Jhon Doe",
      userId: 4,
      profilePic: "https://robohash.org/doloremquesintcorrupti.png",
      desc: "Lorem ipsum dolor sit amet consectetur, adipisicing elit. Itaque labore vero ratione molestiae, modi iste saepe dignissimos voluptatum ipsam repellat temporibus qui ducimus reprehenderit nam debitis omnis veritatis enim! Saepe!",
      img: "https://robohash.org/consequunturautconsequatur.png",
    },
  ];

  return (
    <div className="posts">
      {posts.map((post) => (
        <Post post={post} key={post.id}/>
      ))}
    </div>
  );
};

export default Posts;
