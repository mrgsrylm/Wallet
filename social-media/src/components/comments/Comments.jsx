import "./comments.scss";

const Comments = () => {
  const comments = [
    {
      id: 1,
      desc: "Lorem ipsum dolor sit amet consectetur",
      name: "Jane Doe",
      userId: 1,
      profilePicture: "https://robohash.org/consequunturautconsequatur.png",
    },
    {
      id: 2,
      desc: "Lorem ipsum dolor sit amet consectetur",
      name: "Jane Doe",
      userId: 2,
      profilePicture: "https://robohash.org/consequunturautconsequatur.png",
    },
  ];

  return (
    <div className="comments">
      {comments.map((comment) => (
        <div className="comment">
          <img src={comment.profilePicture} alt="" />
          <div className="info">
            <span>{comment.name}</span>
            <p>{comment.desc}</p>
          </div>
          <span className="date">1 min ago</span>
        </div>
      ))}
    </div>
  );
};

export default Comments;
