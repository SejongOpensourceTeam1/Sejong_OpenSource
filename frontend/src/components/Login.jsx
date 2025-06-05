import "./Modal.css";
import { useState } from "react";

// Login 컴포넌트: 로그인 모달 UI + 로그인 로직
const Login = ({ closeModal, onLoginSuccess, switchToRegister }) => {
  // 폼 입력 상태 (아이디, 비밀번호)
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  // 입력 필드 값 변경 핸들러
  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value, // name에 따라 username 또는 password 수정
    }));
  };

  // 폼 제출 시 로그인 요청
  const handleSubmit = async (e) => {
    e.preventDefault(); // 폼 기본 제출 방지

    try {
      const res = await fetch(
        `${import.meta.env.VITE_BACKEND_API_URL}/api/login`, // 로그인 API 호출
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(formData), // 사용자 입력 전송
        }
      );

      if (res.ok) {
        const result = await res.json();

        // JWT 토큰 로컬스토리지 저장
        localStorage.setItem("accessToken", result.accessToken);
        // localStorage.setItem("username", result.username); // 필요 시 사용

        console.log(result);
        onLoginSuccess(); // 상위 컴포넌트에 로그인 성공 알림
        closeModal(); // 모달 닫기
        window.location.reload(); // 새로고침 (UI 갱신용)
      } else {
        alert("로그인 실패: 아이디나 비밀번호를 확인하세요.");
      }
    } catch (err) {
      console.error(err);
      alert("서버 연결 오류");
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        {/* 닫기 버튼 (x) */}
        <button className="close-button" onClick={closeModal}>
          ×
        </button>

        <h2>로그인</h2>

        {/* 로그인 폼 */}
        <form onSubmit={handleSubmit} className="modal-form">
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
          <button type="submit" className="modal-submit">
            로그인
          </button>
        </form>

        {/* 회원가입 이동 링크 */}
        <p className="redirect-text">
          계정이 없으신가요?{" "}
          <span className="link-text" onClick={switchToRegister}>
            회원가입
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;
