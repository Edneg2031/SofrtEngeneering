<template>
    <div>
        <!--------------------------------------面包屑导航区域------------------------------------>
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!------------------------------------卡片视图区域------------------------------------------>
        <el-card class="box-card">
            <el-row :gutter="20">
                <el-col :span="8" class="searchArea">
                    <!-- 搜索 clearable:清空数据，@clear:查询数据-->

                        <el-input type="text" clearable placeholder="请输入昵称"  prefix-icon="el-icon-search" v-model="queryParams.name">
                        <el-button
                            slot="append"
                            icon="el-icon-search"
                            @click="getUserInfoListByPage(1)"
                        ></el-button>


                        </el-input>
                </el-col>
                <!--------------------------- 添加用户 -------------------------------->
                <el-col :span="10" >
                    <el-select v-model="selectedLanguage" placeholder="选择语言" style="width: 120px; margin-left: 10px;">
                        <el-option label="English" value="en-US"></el-option>
                        <el-option label="中文" value="zh-CN"></el-option>
                    </el-select>

                    <el-button  type="primary" @click="startSpeechRecognition">🎤</el-button>
                    <el-button type="primary" @click="handleAdd">添加用户</el-button>
                </el-col>
            </el-row>

            <!--------------------------------------------- 用户列表区域 ----------------------------------------->
            <el-table :data="userList" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="姓名" prop="username"></el-table-column>
                <el-table-column label="密码" prop="password"></el-table-column>
                <el-table-column label="邮箱" prop="email"></el-table-column>
                <el-table-column label="角色" prop="role"></el-table-column>
                <el-table-column label="创建时间" prop="createTime"></el-table-column>
                <el-table-column label="余额" prop="balance"></el-table-column>
                <el-table-column label="操作" width="180px">
                    <template slot-scope="scope">
                        <!-------------------------------- 修改用户按钮 ---------------------------------->
                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleUpdate(scope.row)"></el-button>
                        <!------------------------------- 删除用户按钮 ------------------------------>
                        <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDelete(scope.row)"></el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                background
                @size-change="sizeChange"
                @current-change="getUserInfoListByPage"
                :current-page="queryParams.page"
                :page-sizes="[1,3,5,10]"
                :page-size="queryParams.size"
                layout="total, sizes, prev, pager, next"
                :total="total">
            </el-pagination>
        </el-card>

        <!--dialog 的样式-->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
            <el-form :model="form"  ref="form" label-width="80px" >
                <el-form-item label="姓名" prop="username">
                    <el-input type="text" v-model="form.username" placeholder="姓名"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="text" v-model="form.password" placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input type="text" v-model="form.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item v-if="!isAdd" label="角色" prop="role">
                    <el-radio-group v-model="form.role">
                        <el-radio label="administrator">Administrator</el-radio>
                        <el-radio label="user">User</el-radio>
                    </el-radio-group>
                </el-form-item>
<!--                <el-form-item label="时间" prop="zip">-->
<!--                    <el-input type="text" v-model="form.createTime" placeholder="时间"></el-input>-->
<!--                </el-form-item>-->
                <el-form-item label="余额" prop="zip">
                    <el-input type="text" v-model="form.balance" placeholder="余额"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>

        </el-dialog>
    </div>
</template>
<script>
import user from "@/api/user";

export default {
    data() {
        return {
            // 定义获取用户列表参数（queryInfo），query:查询，pagenum:当前页数，pagesize:当前每页显示多少条数据
            queryParams:{page:1,size:5,name:''},
            // 获取用户列表数据,存放到userList中,查询条数放到total中
            userList: [],
            total: 0,
            isAdd:false,
            // 弹出层信息
            title:'',       // 标题
            type:0,         // 0表示新增，1表示修改
            open:false,     // 是否弹出
            // 和表单有关的数据绑定，其中rules表示验证规则
            form:{
                userId:'',
                username:'',
                password:'',
                email:'',
                role:'',
                createTime:'',
                balance:''
            },
            selectedLanguage: 'en-US' // Default language
        }
    },
    props: {
        userInfo: {
            type: Object,
            required: true
        }
    },
    created() {
        // this.getUserList()
        this.getUserInfoListByPage(1)
    },
    methods: {

        // 获取用户列表数据（getUserList）
        // async getUserList() {
        //     this.userList = [{user_id:1,username:"kobe",password:"123", email:"kobe@qq.com",role:"user",  create_time:"2024-05-30T19:20:07",balance:10 }]
        // },
        // async getUserInfoListByPage(page=1){
        //     this.queryParams.page = Number(page)
        //     this.queryParams.name = this.queryParams.name.toString(); // 将name参数转换为字符串类型
        //     this.queryParams.size = Number(this.queryParams.size)
        //
        //     user.getUserInfoListByPage(this.queryParams)
        //         .then((res)=>{
        //             // 获取查询到的数据
        //
        //             this.userList  = res.data.records
        //             this.total = res.data.total
        //         }).catch(e=>{
        //         console.log(e)
        //     })
        //
        // },
        async getUserInfoListByPage(page = 1, stayOnCurrentPage = false) {
            this.queryParams.page = Number(page);
            this.queryParams.name = this.queryParams.name.toString(); // Convert name parameter to string type
            this.queryParams.size = Number(this.queryParams.size);

            try {
                const res = await user.getUserInfoListByPage(this.queryParams);
                this.userList = res.data.records;
                this.total = res.data.total;

                if (this.userList.length === 0 && this.queryParams.page > 1 && !stayOnCurrentPage) {
                    this.getUserInfoListByPage(this.queryParams.page - 1);
                }
            } catch (e) {
                console.log(e);
            }
        },
        // 更改每一页的大小后要让其到第一个界面
        sizeChange(e){
            this.queryParams.size = e
            // this.getListRemote(1)
            this.getUserInfoListByPage(1)
        },
        emptyForm(){
            this.form.userId='',
            this.form.username='',
            this.form.password='',
            this.form.email='',
            this.form.role='',
            this.form.createTime='',
            this.form.balance=''
        },

        // 操作的逻辑
        handleAdd(){
            this.emptyForm()
            this.isAdd = true;
            this.type = 0;
            this.title="新增用户"
            this.open=true

        },
        handleUpdate(data) {
            this.isAdd = false;
            // 通过表单获取用户的信息
            this.type = 1
            this.title = "修改用户"
            this.open = true
            this.form.userId = data.userId
            this.form.username = data.username
            this.form.password = data.password
            this.form.email = data.email
            this.form.role = data.role
            this.form.createTime  = data.createTime
            this.form.balance = data.balance

        },
        handleDelete(row) {
            if (this.userInfo.userId === row.userId) {
                this.$message.error("无法删除自己");
                return;
            }
            this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(async () => {
                const userId = Number(row.userId); // Convert string type to number type
                try {
                    await user.deleteUserInfo(userId);
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    // Check if we need to navigate to the previous page
                    const currentPage = this.queryParams.page;
                    const remainingUsers = this.userList.length - 1; // remaining users after deletion

                    if (remainingUsers === 0 && currentPage > 1) {
                        this.getUserInfoListByPage(currentPage - 1);
                    } else {
                        this.getUserInfoListByPage(currentPage, true);
                    }
                } catch (error) {
                    this.$message({
                        type: 'error',
                        message: '删除失败!'
                    });
                }
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        // dialog的逻辑
        submitForm() {
            // 0 表示新增操作
            if(this.type === 0){
                // 进行添加
                console.log(this.form)
                user.addUserInfo(this.form)
                    .then(response => {
                        this.$message.success('添加成功');
                        this.getUserInfoListByPage(this.queryParams.page)
                    })
                    .catch(error => {
                        console.error('添加失败:', error);
                        this.$message.error('添加失败');

                    });
            }else{

                user.updateUserInfo(this.form)
                    .then(response => {
                        console.log('修改成功:', response);
                        this.$message.success('修改成功');
                        this.getUserInfoListByPage(this.queryParams.page)
                    })
                    .catch(error => {
                        console.error('修改失败:', error);
                        this.$message.error('修改失败');
                    });
            }
            this.open = false;
            this.emptyForm()
            // this.getUserInfoListByPage(this.queryParams.page)
        },
        cancel(){
            this.open =false
            this.emptyForm()
        },
        startSpeechRecognition() {
            if (!('webkitSpeechRecognition' in window)) {
                this.$message.error('Your browser does not support speech recognition. Please use Google Chrome.');
                return;
            }

            const recognition = new webkitSpeechRecognition();
            recognition.lang = this.selectedLanguage;
            recognition.interimResults = false;
            recognition.maxAlternatives = 1;

            recognition.start();

            recognition.onresult = (event) => {
                let speechResult = event.results[0][0].transcript.toLowerCase();
                speechResult = speechResult.replace(/[。，！.,/#!$%^&*;:{}=\-_`~()]/g, ''); // Remove punctuation
                this.queryParams.name = speechResult;
                this.getUserInfoListByPage(1); // Trigger the search with the recognized speech text
            };

            recognition.onerror = (event) => {
                console.error('Speech recognition error', event);
                this.$message.error('Speech recognition error. Please try again.');
            };

            recognition.onend = () => {
                console.log('Speech recognition service disconnected');
            };
        }

    },
}
</script>

<style scoped>
.searchArea{
    margin-bottom: 10px;
}
.el-breadcrumb{
    margin-bottom: 40px;
    font-size: 20px;
}
.box-card{
  line-height: normal;
}

</style>