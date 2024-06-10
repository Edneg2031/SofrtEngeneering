import axios from 'axios'
import {Message} from 'element-ui'


// 创建一个axios示例
const service = axios.create({
    baseURL:"http://localhost:8090",
    timeout:5000
})


// 拦截请求
// service.interceptors.request.use(
//     config=>{},
//     error=>{}
// )


// 相应拦截
// service.interceptors.response.use(
//     response => {},
//     error => {}
// )
export default service