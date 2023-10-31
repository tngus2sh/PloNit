import React, { useState, useEffect, useRef } from "react";

function useCamera() {
  const [image, setImage] = useState<string | null>(null);
  const fileInputRef = useRef<HTMLInputElement | null>(null);

  const handleImageCapture = () => {
    if (fileInputRef.current) {
      fileInputRef.current.click();
    }
  };

  useEffect(() => {
    function handleFileInputChange(e: Event) {
      const target = e.target as HTMLInputElement;
      const file = target.files && target.files[0];
      if (file) {
        const imageUrl = URL.createObjectURL(file);
        setImage(imageUrl);
      }
    }
    if (fileInputRef.current) {
      fileInputRef.current.addEventListener("change", handleFileInputChange);
    }

    return () => {
      if (fileInputRef.current) {
        fileInputRef.current.removeEventListener(
          "change",
          handleFileInputChange,
        );
      }
    };
  }, []);

  return { image, handleImageCapture, fileInputRef };
}

export default useCamera;
