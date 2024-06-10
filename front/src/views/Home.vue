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
                            <el-menu-item index="1-1" @click="goUserManage" >用户管理</el-menu-item>
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
                        </div>
                        <div class="part-right">
                            <el-calendar class="calendar" v-model="value" v-if="showCalendar"></el-calendar>
                            <el-table :data="tableData" :show-header="false" border style="width: 100%" class="UserInfo" v-else>
                                <el-table-column prop="label" width="120"></el-table-column>
                                <el-table-column prop="value"></el-table-column>
                            </el-table>
                        </div>
                    </div>
                    <router-view v-show="!showFront"></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>

import router from "@/router";

export default {
    data(){
        return{

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
        }
    },
    methods:{
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
            this.userAvatarUrl = `http://localhost:8090/user/avatar/${this.userInfo.userId}`;
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
        }
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