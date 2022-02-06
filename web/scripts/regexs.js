const REGEX_PHONE = new RegExp(/^[0-9]{3}[\ \.-]?[0-9]{3}[\ \.-]?[0-9]{4}$/);
const REGEX_EMAIL = new RegExp(/^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/);
const REGEX_NUMBERS = new RegExp(/^[0-9-.]*$/);
//const REGEX_LETTERS = new RegExp(/^[a-zA-Z]*$/);
const REGEX_LETTERS = new RegExp(/^[ \-.\p{L}]*$/u);
const REGEX_NOT_EMPTY = new RegExp(/.+/);
const REGEX_POSTAL_CODE = new RegExp(/^[A-Z][0-9][A-Z][ ,-]?[0-9][A-Z][0-9]$/);