const registerAxios = (params) => {
    return $axios({
        url: '../../json/common/success.json',
        method: 'post',
        data: {...params}
    })
}