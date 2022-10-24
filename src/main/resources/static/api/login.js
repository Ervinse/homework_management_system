// 发送验证码
const sendMessageAxios = (phoneNumber) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'get',
        params: {
            phoneNumber: phoneNumber
        }
    })
}
//重置密码
const resetPasswordAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
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
        url: '../../json/common/success.json',
        method: 'post',
        data: {...params}
    })
}
