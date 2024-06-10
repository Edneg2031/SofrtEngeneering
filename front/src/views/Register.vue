<template>
    <div class="background">
        <div class="register">
            <div class="title">
                注册
            </div>
            <div class="main">
                <el-form :model="User" :rules="rules" ref="registerForm">
                    <div class="main-part">
                        <div class="left-part">
                            <el-form-item prop="name" >
                                <el-input v-model="User.username" type="text" class="inputStyle" placeholder="用户名"></el-input>
                            </el-form-item>
                            <el-form-item prop="password" >
                                <el-input v-model="User.password" type="password" class="inputStyle" placeholder="密码"></el-input>
                            </el-form-item>
                            <el-form-item prop="email" >
                                <el-input v-model="User.email" type="text" class="inputStyle" placeholder="电子邮箱"></el-input>
                            </el-form-item>

                        </div>
                        <img src="../assets/Divider.svg" class="mid"/>
                        <div class="right-part">
                            <el-form-item class="right">
                                <h3>上传头像</h3>
                                <el-upload
                                    ref="upload"
                                    :action="uploadUrl"
                                    :data="uploadData"
                                    :show-file-list="false"
                                    :auto-upload="false"
                                    :before-upload="beforeAvatarUpload"
                                    :on-success="handleAvatarSuccess"
                                    :on-error="handleAvatarError"
                                    :on-change="handleAvatarChange">
                                    <img v-if="avatarUrl" :src="avatarUrl" alt="头像预览" class="avatar" @click="selectFile"/>
                                    <div v-else class="avatar-placeholder" @click="selectFile">点击上传头像</div>
                                </el-upload>
<!--                                <button @click="uploadTri">上传</button>-->
                            </el-form-item>
                        </div>
                    </div>
                </el-form>
            </div>
            <div class="foot">
                <button @click="onSubmit" class="footStyle">确认</button>
                <button class="footStyle" @click="backLogin()">返回登录</button>
            </div>
        </div>
    </div>
</template>


<script>
import user from "@/api/user";
import axios from "axios";

export default {
    name: "register",
    data() {
        return {
            User: {
                username: '',
                password: '',
                email: '',
            },
            uploadData: {
                userId: null
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ],
                email: [
                    { required: true, message: '请输入电子邮箱', trigger: 'blur' },
                    { type: 'email', message: '请输入有效的电子邮箱地址', trigger: ['blur', 'change'] }
                ],
            },
            avatarUrl: '',
            uploadUrl: 'http://localhost:8090/upload/avatar'
        };
    },
    methods: {
        onSubmit() {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    user.register(this.User)
                        .then(response => {
                            // 设置上传数据中的 userId
                            this.uploadData.userId = response.data;
                            console.log(this.uploadData.userId)
                            // 上传图片
                            this.$refs.upload.submit();
                            this.$message.success('注册成功');

                        })
                        .catch(error => {
                            this.$message.error('注册失败');
                        });

                } else {
                    console.log('表单验证失败');
                    return false;
                }
            });
        },
        emptyUser() {
            this.User.name = '';
            this.User.email = '';
            this.User.password = '';
        },
        backLogin() {
            this.$router.push('/');
        },
        uploadTri(){
            this.uploadData.userId = 10;
            // 上传图片
            this.$refs.upload.submit();
        },
        selectFile() {
            this.$refs.upload.$refs.input.click();
        },
        handleAvatarChange(file, fileList) {
            this.avatarUrl = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isPNG = file.type === 'image/png';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG && !isPNG) {
                this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
                return false;
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
                return false;
            }
            return true;
        },
        handleAvatarSuccess(response, file, fileList) {
            this.avatarUrl = response.url;
            // this.$message.success('上传成功');
        },
        handleAvatarError(err, file, fileList) {
            this.$message.error('上传失败');
        },

    }
}
</script>

<style scoped>
* {
    margin: 0px;
    padding: 0px;
}
.background {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url("../assets/background.jpg");
    background-size: cover;
    z-index: -1;
    display: flex;
    justify-content: center;
    align-items: center;
}
.register {
    background-color: rgba(255, 255, 255, 0.5);
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
    width: 80%;
    height: 50%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.title {
    text-align: center;
    font-size: 40px;
    font-weight: bold;
}
.main {
    width: 80%;
    margin: auto;
}
.main-part {
    display: flex;
    justify-content: space-between;
}
.left-part, .right-part {
    width: 45%;
}
.right {
    text-align: center;
}
.left-part {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
}
.right-part {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.avatar {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    cursor: pointer;
    object-fit: cover;
}

.avatar-placeholder {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    background-color: #f5f5f5;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    border: 1px dashed #d9d9d9;
    text-align: center;
}

.avatar-placeholder:hover {
    border-color: #409EFF;
}
.inputStyle {
    height: 50px;
    border-radius: 10px;
    /*margin-top: 20px; !* 增加上下间距，确保提示信息不被遮挡 *!*/
    font-size: 25px;
}

.foot {
    margin-top: 60px;
    display: flex;
}
.footStyle {
    display: inline-block;
    width: 100%;
    height: 50px;
    background-color: #505050;
    text-align: center;
    color: white;
    font-size: 20px;
}
.footStyle:hover {
    background-color: black;
}
@media (max-width: 850px) {
    .forget {
        height: 90%;
    }
    .mid {
        display: none;
        flex-grow: 1;
    }
    .main-part {
        display: block;
        width: 100%;
    }
    .left-part, .right-part {
        width: 100%;
    }
    .right-part {
        margin-top: 30px;
    }
    .foot {
        display: block;
    }
}
</style>