<template>
    <div style="height: 100%; width: 100%;">
        <el-container style="height: 100vh; width: 100%; border: 1px solid #eee;">
            <el-aside width="15%" style="background-color: rgb(245, 245, 245);" class="aside-left">
                <el-menu default-active="2" class="el-menu-vertical-demo">
                    <el-menu-item index="0" :class="{'blue-aside': isBlue, 'yellow-aside': isYellow}" @click="goFrontPage">首页</el-menu-item>
                    <el-submenu index="1" :class="{'blue-aside': isBlue, 'yellow-aside': isYellow}">
                        <template slot="title">
                            <i class="el-icon-location"></i>
                            <span>功能菜单</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item v-if="userInfo.role === 'administrator'" index="1-1" @click="goUserManage" >用户管理</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="2" :class="{'blue-aside': isBlue, 'yellow-aside': isYellow}">
                        <template slot="title">
                            <i class="el-icon-location"></i>
                            <span>风格切换</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="2-1" @click="blueStyle">蓝色风格</el-menu-item>
                            <el-menu-item index="2-2" @click="yellowStyle">黄色风格</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header :class="{'blue-header': isBlue, 'yellow-header': isYellow}">
                    <el-dropdown>
                        <span class="el-dropdown-link">
                            登录用户 <i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="showUserInfo">{{ changeInfo }}</el-dropdown-item>
                            <el-dropdown-item @click.native="goLogin">退出</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-header>
                <el-main :class="{'blue-main': isBlue, 'yellow-main': isYellow}">
                    <div class="front" v-if="showFront">
                        <div class="part-left">
                            <h2>欢迎, {{ userInfo.username }}</h2>
                            <div class="container-img">
                                <img class="headshot" :src="userAvatarUrl" />

                            </div>
                            <el-upload
                                ref="upload"
                                :action="uploadUrl"
                                :data="uploadData"
                                :show-file-list="true"
                                :auto-upload="false"
                                :before-upload="beforeAvatarUpload"
                                :on-success="handleAvatarSuccess"
                                :on-error="handleAvatarError"
                                :on-change="handleAvatarChange">
                                <el-button  @click="selectFile">上传</el-button>
                            </el-upload>
                            <el-button @click="uploadTri">确认</el-button>
                        </div>
                        <div class="part-right">
                            <el-calendar class="calendar" v-model="value" v-if="showCalendar"></el-calendar>
                            <el-table :data="tableData" :show-header="false" border style="width: 100%" class="UserInfo" v-else>
                                <el-table-column prop="label" width="120"></el-table-column>
                                <el-table-column prop="value"></el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <router-view :userInfo="userInfo" v-show="!showFront"></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>

import router from "@/router";
import { EventBus } from '../eventBus';
export default {
    data(){
        return{
            uploadData: {
                userId: null
            },
            avatarUrl: '',
            uploadUrl: 'http://localhost:8090/upload/avatar',
            showFront: true ,// 默认显示

            changeInfo:"个人信息",
            tableData: "",
            showCalendar:true,
            isBlue:false,
            isYellow:false,
            value: new Date(),
            userAvatarUrl:'',

            // 传入的用户信息
            userInfo:null
        }
    },
    created() {
        if(this.$route.query && this.$route.query.userInfo){
            this.userInfo = JSON.parse(this.$route.query.userInfo)

            this.getAvatar()
            this.parseUserInfo()
            this.uploadData.userId = this.userInfo.userId
        }
    },
    methods:{
        uploadTri(){
            // this.uploadData.userId = 10;
            // 上传图片
            this.$refs.upload.submit();


        },
        goFrontPage(){

            this.showFront = true;
        },
        showUserInfo(){

            if(this.showCalendar == true) {
                this.showCalendar = false;
                this.changeInfo = "日历"
            }
            else {
                this.showCalendar = true

                this.changeInfo = "个人信息"
            }

        },
        parseUserInfo() {
            this.tableData = [
                { label: '用户名', value: this.userInfo.username },
                { label: '邮箱', value: this.userInfo.email },
                { label: '角色', value: this.userInfo.role },
                { label: '创建时间', value: this.userInfo.createTime },
                { label: '余额', value: this.userInfo.balance }
            ];
        } ,

        getAvatar() {
            this.userAvatarUrl = `http://localhost:8090/user/avatar/${this.userInfo.userId}?${new Date().getTime()}`;
        },
        goUserManage(){
            this.showFront = false;
            if(this.$router.currentRoute.path!=='/home/user') {
                this.$router.push("/home/user")
            }
        },
        goArticleManage(){
            this.showFront = false;
            if(this.$router.currentRoute.path!=='/home/article') {
                this.$router.push("/home/article")
            }
        },
        goLogin(){
            this.$router.replace("/")
        },
        blueStyle(){
            this.isBlue = true;
            this.isYellow = false;
        },
        yellowStyle(){
            this.isBlue = false;
            this.isYellow = true;
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
            this.getAvatar();
            // this.$message.success('上传成功');
        },
        handleAvatarError(err, file, fileList) {
            this.$message.error('上传失败');
        },
    }

}
</script>
<style scoped>
.front{
    display: flex;
    justify-content: space-between;
    line-height: normal;
}
.part-left{
    width: 20% ;
    display: flex;
    margin-left: 100px;
    flex-direction: column;
    align-items: center;

}
.calendar{
    width: 80%;
}
.container-img{
    border-radius: 50%;

}
.headshot{
    aspect-ratio: 1/1; /* 设置宽高比为 1:1 */
    width: 100%;
    height: auto;
    border-radius: 50%;
    object-fit: cover;
}
.UserInfo{
    margin-top: 10%;
}
.container-img{
    margin-top: 10px;
    display: flex;
    background-color: #ffffff;
    justify-content: center;
    width: 100%; /* 设置容器宽度为100% */
}
.el-calendar-table .el-calendar-day {
    box-sizing: border-box;
    padding: 8px;
    height: 40px;
}
.part-right{
    width: 70%;
}
.el-header{
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
}
.el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
}
.el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
}
.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
    line-height: 260px;
}


/*设置样式*/
.blue-header {
    background-color: #409eff;
    color: white;
}

.yellow-header {
    background-color: #f7ba2a;
    color: white;
}

.blue-main {
    background-color: #e6f7ff;
}

.yellow-main {
    background-color: #fff7e6;
}

.blue-aside {
    background-color: #409eff !important;
    color: white !important;
}

.yellow-aside {
    background-color: #f7ba2a !important;
    color: white !important;
}

@media (max-width: 1000px) {
    .aside-left{
        display: none;
    }
}

</style>