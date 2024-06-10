<template>
    <div class="background">
        <div class="login">
            <div class="title">
                登录
            </div>
            <div class="main">
                <div class="quickLogin">
                    <div class="spacer"></div> <!-- 左侧的空白元素 -->
                    <div class="singleStyle qq" v-on:click="quickLogin('QQ登录成功')">
                        <img class="iconStyle" src="@/assets/icon/QQ.png">
                        QQ登录
                    </div>
                    <div class="singleStyle wechat" v-on:click="quickLogin('微信登录成功')">
                        <img class="iconStyle" src="@/assets/icon/Wechat.png">
                        微信登录
                    </div>
                    <div class="singleStyle alipay" v-on:click="quickLogin('支付宝登录成功')">
                        <img class="iconStyle" src="@/assets/icon/Alipay.png">
                        支付宝登录
                    </div>
                    <div class="spacer"></div> <!-- 左侧的空白元素 -->
                </div>
                <el-divider direction="vertical" class="divider"></el-divider>
                <div class="normalLogin">
                    <div class="spacer"></div> <!-- 左侧的空白元素 -->
                    <el-input v-model="User.username" class="inputStyle" type="text" placeholder="用户名"/>
                    <el-input v-model="User.password" class="inputStyle" type="password" placeholder="密码"/>
                    <el-button class="btn" v-on:click="onLogin">登录</el-button>
                    <div class="spacer"></div> <!-- 左侧的空白元素 -->
                </div>
            </div>
            <div class="foot">
                <button class="footStyle" @click="goRegister">注册</button>
                <button class="footStyle" @click="goForgetPassword">修改密码</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import router from "@/router";
import user from "@/api/user";

export default {
    name: "login",
    data(){
        return{
            User: {
                username: '',
                password: '',
            },
        }
    },
    methods:{
        // 快速登录
        quickLogin(msg){
            alert(msg)
        },
        // 页面跳转
        goRegister(){
            this.$router.push("/register")
        },
        goForgetPassword(){
            this.$router.push("/changePassword")
        },

        // 进行登录
        onLogin(){
            // 添加登录的逻辑
            user.login(this.User).then(response=>{
                console.log(JSON.stringify(response.data))
                this.$router.push({
                    path: '/home',
                    query: { userInfo: JSON.stringify(response.data) }  // 将用户信息作为查询参数传递
                });
            }).catch(error=>{
                this.$message.error('登录失败');
            });
        }
    }

}
</script>

<style scoped>
    *{
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

        z-index: -1; /* 将背景图层级设置为最底层 */
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .login{
        background-color: rgba(255, 255, 255, 0.5);
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5); /* 添加阴影效果 */
        margin:auto;
        width: 80%;
        height: 50%;
        display: flex; /* 使用 Flexbox 布局 */
        flex-direction: column; /* 垂直排列元素 */
        justify-content: space-between; /* 将元素分散对齐 */
    }
    .title{
        text-align: center;
        font-size: 40px;
        font-weight: bold;
    }
    .main{
        width: 80%;
        margin:auto;
        height: 70%;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .quickLogin{
        width: 40%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
    }
    .normalLogin{
        width: 40%;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-around;
    }

    .singleStyle{
        height: 50px;
        display: flex;
        align-items: center;
        color:white;
        padding-left:10px;
        font-size: 20px;
        border-radius: 10px;
    }
    .iconStyle{
        height: 80%;
        margin-right: 10px;
    }
    .qq{
        background-color: #4186c5;
    }
    .wechat{
        background-color: #51af2c;
    }
    .alipay{
        background-color: #eaebea;
        color:#000
    }

    .inputStyle{
        height: 50px;
        border-radius: 10px;
        font-size: 25px;
    }
    .btn {
        background-color: #4CAF50; /* 设置背景颜色 */
        color: white; /* 设置文字颜色 */
        border: none; /* 移除边框 */
        padding: 10px 20px; /* 设置内边距 */
        text-align: center; /* 文字居中 */
        text-decoration: none; /* 移除下划线 */
        display: inline-block; /* 设置为行内块元素 */
        font-size: 16px; /* 设置字体大小 */
        cursor: pointer; /* 设置鼠标样式为指针 */
        border-radius: 5px; /* 设置圆角 */
        transition-duration: 0.4s; /* 设置过渡效果持续时间 */
    }

    .btn:hover {
        background-color: #45a049; /* 鼠标悬停时改变背景颜色 */
    }

    .foot{
        margin-top:60px;
        display: flex;
    }
    .footStyle{
        display:inline-block;
        width: 100%;
        height: 50px;
        width: 100%;
        background-color: #505050;
        text-align: center;
        color:white;
        font-size: 20px;
    }
    .divider{
        height: 200px;
    }
    .footStyle:hover{
        background-color: black;
    }
    @media (max-width: 650px) {

        .main{
            width: 80%;
            margin:auto;
            display: block;
        }

        .divider{
            display: none;
        }
        .quickLogin{

            width: 100%;
            height: 50%;
        }
        .normalLogin{
            height: 50%;
            margin-top: 20px;
            width: 100%;
        }
        .foot{
            display: block;
        }

    }

</style>