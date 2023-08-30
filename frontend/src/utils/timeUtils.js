export function getLocalTime(ms) {
    return new Date(parseInt(ms)).toLocaleString().replace(/:\d{1,2}$/, ' ');
}