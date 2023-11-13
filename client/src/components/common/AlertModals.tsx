import Swal from "sweetalert2";

interface LibModalInterface {
  title?: string;
  text: string;
  footer?: string;
}

export const NotOkModal = ({ title, text, footer }: LibModalInterface) => {
  return Swal.fire({ icon: "error", title, text, footer });
};

export const OkModal = ({ title, text, footer }: LibModalInterface) => {
  return Swal.fire({ icon: "success", title, text, footer });
};

export const QuestionModal = ({ title, text, footer }: LibModalInterface) => {
  return Swal.fire({
    icon: "warning",
    showCancelButton: true,
    confirmButtonColor: "#3085d6",
    confirmButtonText: "확인",
    cancelButtonText: "취소",
    title,
    text,
    footer,
  });
};

// const onTest = () => {
//     QuestionModal({ title: "다시", text: "금액을 입력하세요." }).then((res) => {
//       if (res.isConfirmed) {
//         setOk(1);
//       } else {
//         setOk(2);
//       }
//     });
//   };
