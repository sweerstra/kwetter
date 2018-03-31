export const debounce = (fn, time) => {
    let timeout;

    return function () {
        const call = () => fn.apply(this, arguments);

        clearTimeout(timeout);
        timeout = setTimeout(call, time);
    }
};