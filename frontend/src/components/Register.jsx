import "./Modal.css";
import { useState } from "react";

const Register = ({ closeModal, switchToLogin }) => {
  const [formData, setFormData] = useState({
    nickname: "",
    username: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    try {
      const res = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/api/register`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nickname: formData.nickname,
            username: formData.username,
            password: formData.password,
          }),
        }
      );

      if (res.ok) {
        alert("회원가입 성공!");
        switchToLogin(); // 바로 로그인 모달로 전환
      } else {
        alert("회원가입 실패");
      }
    } catch (err) {
      console.error(err);
      alert("오류 발생");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <button className="close-button" onClick={closeModal}>
          ×
        </button>
        <h2>회원가입</h2>
        <form onSubmit={handleSubmit} className="modal-form">
          <input
            type="text"
            name="nickname"
            placeholder="닉네임"
            value={formData.nickname}
            onChange={handleChange}
            required
          />
          <input
            type="text"
            name="username"
            placeholder="아이디"
            value={formData.username}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="password"
            placeholder="비밀번호"
            value={formData.password}
            onChange={handleChange}
            required
          />
          <input
            type="password"
            name="confirmPassword"
            placeholder="비밀번호 확인"
            value={formData.confirmPassword}
            onChange={handleChange}
            required
          />
          <button type="submit" className="modal-submit">
            가입하기
          </button>
        </form>
        <p className="redirect-text">
          이미 계정이 있으신가요?{" "}
          <span className="link-text" onClick={switchToLogin}>
            로그인
          </span>
        </p>
      </div>
    </div>
  );
};

export default Register;
