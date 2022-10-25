const registerAxios = (params) => {
    return $axios({
        url: '/login/register',
        method: 'post',
        data: {...params}
    })
}