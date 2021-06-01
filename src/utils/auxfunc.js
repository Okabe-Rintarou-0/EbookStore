export function scrollBackToTop() {
    let currentScroll = document.documentElement.scrollTop || document.body.scrollTop;
    if (currentScroll > 0) {
        window.scrollTo(0, 0);
    }
}
