import request from '@/utils/axios.js'
const api_name = 'api/users'
const api_image_name = '/drawing_bed'
export default {

    login(user){
        return request({
            url: `${api_name}/login`,
            method: 'post',
            data: user
        });
    },

    change(userInfo){
        return request({
            url:`${api_name}/change`,
            method:"POST",
            data:userInfo
        })
    },
    register(user){
        return request({
            url: `${api_name}/register`,
            method: 'post',  // 改为 POST 方法
            data: user
        });
    },
    // 获取用户信息
    getUserAndPaper(data){
        return request({
            url:`${api_name}/getUserInfoList`,
            method:"GET",
        })
    },
    getUserInfoListByPage(data){
        return request({
            url:`${api_name}/getUserInfoListByPage`,
            method:"GET",
            params:data
        })
    },
    getUserInfoListByName(name){
        return request({
            url:`${api_name}/getUserInfoListByPage`,
            method:"GET",
            params:name
        })
    },
    searchUsers(data){
        return request({
            url:`${api_name}/getUserList`,
            method:"GET",
            params:data
        })
    },
    addUserInfo(form){
        return request({
            url:`${api_name}/register`,
            method:"POST",
            data:form
        })
    },
    updateUserInfo(form){
        return request({
            url:`${api_name}/updateUserInfo`,
            method:"POST",
            data:form
        })
    },
    deleteUserInfo(id){
        return request({
            url:`${api_name}/deleteUserInfo/${id}`,
            method: 'DELETE', // 使用 HTTP DELETE 方法
        })
    }
}