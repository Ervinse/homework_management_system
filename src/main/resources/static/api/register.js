const registerAxios = (params) => {
    return $axios({
        url: '/student/',
        method: 'post',
        data: {...params}
    })
}