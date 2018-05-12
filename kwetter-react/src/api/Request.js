class Request {
    get(url) {
        return this._request(url);
    }

    post(url, data) {
        return this._request(url, {
            method: 'post',
            body: JSON.stringify(data)
        });
    }

    put(url, data) {
        return this._request(url, {
            method: 'put',
            body: JSON.stringify(data)
        });
    }

    delete(url, data) {
        return this._request(url, {
            method: 'delete',
            body: JSON.stringify(data)
        });
    }

    _request(url, options = {}) {
        const headers = { ...options.headers, 'Content-Type': 'application/json' };

        const token = localStorage.getItem('access_token');

        if (token) {
            headers.Authorization = token;
        }

        return fetch(url, { ...options, headers })
            .then(resp => resp.json());
    };
}

export default new Request();