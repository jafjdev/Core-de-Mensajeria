package Entities.M01_Login;

import Entities.Entity;

import java.sql.Date;
import java.util.Objects;

public class User extends Entity {

    private int _idUser;
    private String _passwordUser;
    private String _usernameUser;
    private int _typeUser; //1. superusuario, 2. administrador, 3. creador,4. aprobador, 5. consultor
    private String _emailUser;
    private String _phoneUser;
    private Date _dateOfBirthUser;
    private String _countryUser;
    private String _addressUser;
    private String _genderUser;
    private String _cityUser;
    private Integer _blockedUser;
    private Integer _remainingAttemptsUser;
    private String _nameUser;
    private String _lastnameUser;
    private Integer _identificationNumberUser;
    private Integer _rgUser;
    private String _bdUser;

        public User(int _idUser, String _passwordUser, String _usernameUser, int _typeUser, String _emailUser, String _phoneUser, Date _dayOfBirthUser, String _countryUser, String _addressUser, String _genreUser, String _cityUser) {
        this._idUser = _idUser;
        this._passwordUser = _passwordUser;
        this._usernameUser = _usernameUser;
        this._typeUser = _typeUser;
        this._emailUser = _emailUser;
        this._phoneUser = _phoneUser;
        this._dateOfBirthUser = _dayOfBirthUser;
        this._countryUser = _countryUser;
        this._addressUser = _addressUser;
        this._genderUser = _genreUser;
        this._cityUser = _cityUser;
    }

    public User(int _idUser, String _passwordUser, String _usernameUser, int _typeUser, String _emailUser, String _phoneUser, Date _dateOfBirthUser, String _countryUser, String _addressUser, String _genderUser, String _cityUser, String _nameUser, String _lastnameUser, Integer _identificationNumberUser, Integer _rgUser, String _bdUser) {
        this._idUser = _idUser;
        this._passwordUser = _passwordUser;
        this._usernameUser = _usernameUser;
        this._typeUser = _typeUser;
        this._emailUser = _emailUser;
        this._phoneUser = _phoneUser;
        this._dateOfBirthUser = _dateOfBirthUser;
        this._countryUser = _countryUser;
        this._addressUser = _addressUser;
        this._genderUser = _genderUser;
        this._cityUser = _cityUser;
        this._nameUser = _nameUser;
        this._lastnameUser = _lastnameUser;
        this._identificationNumberUser = _identificationNumberUser;
        this._rgUser = _rgUser;
        this._bdUser = _bdUser;
    }

    public User(int idUser) {
            _idUser = idUser;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return //_idUser == user._idUser &&
                _typeUser == user._typeUser &&
                Objects.equals(_usernameUser, user._usernameUser) &&
                Objects.equals(_emailUser, user._emailUser) &&
                Objects.equals(_phoneUser, user._phoneUser) &&
                Objects.equals(_dateOfBirthUser, user._dateOfBirthUser) &&
                Objects.equals(_countryUser, user._countryUser) &&
                Objects.equals(_addressUser, user._addressUser) &&
                Objects.equals(_genderUser, user._genderUser) &&
                Objects.equals(_cityUser, user._cityUser);

    }

    @Override
    public int hashCode() {
        return Objects.hash(/*_idUser, */_passwordUser, _usernameUser, _typeUser, _emailUser, _phoneUser, _dateOfBirthUser, _countryUser, _addressUser, _genderUser, _cityUser);
    }

    public int get_idUser() {
        return _idUser;
    }

    public void set_idUser(int _idUser) {
        this._idUser = _idUser;
    }

    public String get_passwordUser() {
        return _passwordUser;
    }

    public void set_passwordUser(String _passwordUser) {
        this._passwordUser = _passwordUser;
    }

    public String get_usernameUser() {
        return _usernameUser;
    }

    public void set_usernameUser(String _usernameUser) {
        this._usernameUser = _usernameUser;
    }

    public int get_typeUser() {
        return _typeUser;
    }

    public void set_typeUser(int _typeUser) {
        this._typeUser = _typeUser;
    }

    public String get_emailUser() {
        return _emailUser;
    }

    public void set_emailUser(String _emailUser) {
        this._emailUser = _emailUser;
    }

    public String get_phoneUser() {
        return _phoneUser;
    }

    public void set_phoneUser(String _phoneUser) {
        this._phoneUser = _phoneUser;
    }

    public Date get_dateOfBirthUser() {
        return _dateOfBirthUser;
    }

    public void set_dateOfBirthUser(Date _dayOfBirthUser) {
        this._dateOfBirthUser = _dayOfBirthUser;
    }

    public String get_countryUser() {
        return _countryUser;
    }

    public void set_countryUser(String _countryUser) {
        this._countryUser = _countryUser;
    }

    public String get_addressUser() {
        return _addressUser;
    }

    public void set_addressUser(String _addressUser) {
        this._addressUser = _addressUser;
    }

    public String get_genderUser() {
        return _genderUser;
    }

    public void set_genderUser(String _genreUser) {
        this._genderUser = _genreUser;
    }

    public String get_cityUser() {
        return _cityUser;
    }

    public void set_cityUser(String _cityUser) {
        this._cityUser = _cityUser;
    }

    public Integer get_blockedUser() {
        return _blockedUser;
    }

    public void set_blockedUser(Integer _blockedUser) {
        this._blockedUser = _blockedUser;
    }

    public Integer get_remainingAttemptsUser() {
        return _remainingAttemptsUser;
    }

    public void set_remainingAttemptsUser(Integer _remainingAttemptsUser) {
        this._remainingAttemptsUser = _remainingAttemptsUser;
    }

    public String get_nameUser() {
        return _nameUser;
    }

    public void set_nameUser(String _nameUser) {
        this._nameUser = _nameUser;
    }

    public String get_lastnameUser() {
        return _lastnameUser;
    }

    public void set_lastnameUser(String _lastnameUser) {
        this._lastnameUser = _lastnameUser;
    }

    public Integer get_identificationNumberUser() {
        return _identificationNumberUser;
    }

    public void set_identificationNumberUser(Integer _identificationNumberUser) {
        this._identificationNumberUser = _identificationNumberUser;
    }

    public Integer get_rgUser() {
        return _rgUser;
    }

    public void set_rgUser(Integer _rgUser) {
        this._rgUser = _rgUser;
    }

    public String get_bdUser() {
        return _bdUser;
    }

    public void set_bdUser(String _bdUser) {
        this._bdUser = _bdUser;
    }
}
