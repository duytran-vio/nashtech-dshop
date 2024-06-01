import React from "react";
import { message, Upload } from "antd";
import { imagesEndpoint } from "../../../services/imageService";
import api from "../../../services/api";

const beforeUpload = (file) => {
  const isJpgOrPng = file.type === "image/jpeg" || file.type === "image/png";
  if (!isJpgOrPng) {
    message.error("You can only upload JPG/PNG file!");
  }
  const isLt2M = file.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    message.error("Image must smaller than 2MB!");
  }
  return isJpgOrPng && isLt2M;
};
const CategoryImageUpload = (props) => {
  const uploadImage = async (options) => {
    const { onSuccess, onError, file, onProgress } = options;

    const fmData = new FormData();
    const config = {
      headers: { "content-type": "multipart/form-data" },
      onUploadProgress: (event) => {
        onProgress({ percent: (event.loaded / event.total) * 100 });
      },
    };
    fmData.append("file", file);
    try {
      const res = await api.post(imagesEndpoint, fmData, config);
      onSuccess(res.data);
    } catch (err) {
      message.error("upload failed.");
      onError({ err });
    }
  };

  const handleOnChange = ({ file, fileList, event }) => {
    props.setFileList(fileList);
  };

  return (
    <>
      <Upload
        accept="image/*"
        customRequest={uploadImage}
        onChange={handleOnChange}
        beforeUpload={beforeUpload}
        listType="picture-card"
        fileList={props.fileList}
        className="image-upload-grid"
        maxCount={1}
      >
        {props.fileList.length >= 1 ? null : <div>Upload Button</div>}
      </Upload>
    </>
  );
};
export default CategoryImageUpload;
