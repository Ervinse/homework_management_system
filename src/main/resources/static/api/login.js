// 发送验证码
const sendMessageAxios = (accountName,phoneNumber) => {
    return $axios({
        url: '/login/sendMsg',
        method: 'get',
        params: {
            phoneNumber: phoneNumber,
            accountName: accountName
        }
    })
}
//重置密码
const resetPasswordAxios = (params) => {
    return $axios({
        url: '/login/resetPassword',
        method: 'put',
        data: {...params}
    })
}
//登录
const loginAxios = (params) => {
    return $axios({
        url: '/login',
        method: 'post',
        data: {...params}
    })
}
const checkPhoneNumber = (params) => {
    return $axios({
        url: '/login/checkPhoneNumber',
        method: 'post',
        data: {...params}
    })
}


