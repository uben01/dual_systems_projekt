
export function formatHUF(number){
    return Number(number).toLocaleString('hu-HU', {style: 'currency', currency: 'HUF', maximumFractionDigits: 0, useGrouping: true});
}

export function formatEUR(number){
    return Number(number).toLocaleString('de-DE', {style: 'currency', currency: 'EUR', minimumFractionDigits: 0, maximumFractionDigits: 2, useGrouping: true});
}