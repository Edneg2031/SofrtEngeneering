<template>
    <div class="background">
        <div class="changePassword">
            <div class="title">
                修改密码
            </div>
            <div class="main">
                <el-form :model="changeUser" :rules="rules" ref="changePasswordForm">
                    <div class="main-part">
                        <div class="left-part">
                            <el-form-item prop="username" >
                                <el-input v-model="changeUser.username" type="text" class="inputStyle" placeholder="用户名"></el-input>
                            </el-form-item>
                            <el-form-item prop="originPassword" >
                                <el-input v-model="changeUser.originPassword" type="password" class="inputStyle" placeholder="原始密码"></el-input>
                            </el-form-item>
                            <el-form-item prop="newPassword" >
                                <el-input v-model="changeUser.newPassword" type="password" class="inputStyle" placeholder="新密码"></el-input>
                            </el-form-item>
                        </div>
                        <div class="right-part">
                            <el-form-item prop="confirmPassword" >
                                <el-input v-model="changeUser.confirmPassword" type="password" class="inputStyle" placeholder="确认密码"></el-input>
                            </el-form-item>
                            <el-button type="primary" @click="onChangePassword" class="btn">确认</el-button>
                        </div>
                    </div>
                </el-form>
            </div>

            <div class="foot">
                <button class="footStyle" @click="backLogin">返回登录</button>
            </div>
        </div>
    </div>
</template>


<script>
import user from "@/api/user";

export default {
    name: "changePassword",
    data() {
        return {
            changeUser: {
                username: '',
                originPassword:'',
                newPassword: '',
            },
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' }
                ],
                originPassword: [
                    { required: true, message: '请输入原始密码', trigger: 'blur' }
                ],
                newPassword: [
                    { required: true, message: '请输入新密码', trigger: 'blur' }
                ],
                confirmPassword: [
                    { required: true, message: '请确认密码', trigger: 'blur' },
                    { validator: this.validateConfirmPassword, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        validateConfirmPassword(rule, value, callback) {
            if (value !== this.changeUser.newPassword) {
                callback(new Error('两次输入的密码不一致'));
            } else {
                callback();
            }
        },
        onChangePassword() {
            if (this.changeUser.newPassword !== this.changeUser.confirmPassword) {
                this.$message.error('新密码和确认密码不一致');
                return;
            }
            user.change(this.changeUser)
                .then(response => {
                    this.$message.success('密码修改成功');
                    // 清空表单
                    this.changeUser.username = '';
                    this.changeUser.originPassword = '';
                    this.changeUser.newPassword = '';
                    this.changeUser.confirmPassword = '';
                })
                .catch(error => {
                    this.$message.error('密码修改失败');
                });
        },


        backLogin() {
            this.$router.push('/');
        }
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
.changePassword {
    background-color: rgba(255, 255, 255, 0.5);
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
    margin: auto;
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
.inputStyle {
    height: 70px;
    border-radius: 10px;
    margin-top: 20px; /* 增加上下间距，确保提示信息不被遮挡 */
    font-size: 25px;
}
.el-form-item {
    margin-bottom: 20px; /* 增加表单项之间的间距 */
}
.el-form-item__error {
    margin-top: 5px; /* 增加错误提示的顶部间距 */
}
.btn {
    width: 100%;
    background-color: #6aad42;
    color: #fff;
    height: 40px;
    font-size: 25px;
    margin-top: 20px;
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
@media (max-width: 650px) {
    .main-part {
        display: block;
        width: 100%;
    }
    .left-part, .right-part {
        width: 100%;
    }
}
</style>
