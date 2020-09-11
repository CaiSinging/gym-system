import React from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import {withStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import PropTypes from "prop-types";
import axios from "axios";
import Configuration from "../../config";


const styles = (theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

class CustomerResetPwdPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            tel: '',
            email: '',
            newPassword: '',
            repeatPassword: '',
            error: null,
            successMessage: null
        };
    }

    render() {

        const {classes} = this.props;
        const self = this;
        const handleChange = name => event => {
            this.setState({
                [name]: event.target.value,
            });
        };

        const handleSubmit = function () {
            if (self.state.newPassword !== self.state.repeatPassword) {
                alert("两次输入的密码不一致。");
            } else {
                axios.post(Configuration.API_ENDPOINT + "/guest/resetPassword", {
                    tel: self.state.tel,
                    email: self.state.email,
                    newPassword: self.state.newPassword,
                }).then((res) => {
                    alert(res.data.message);
                }).catch((reason) => {
                    self.setState({error: reason.response.data.message});
                    alert(self.state.error);
                });
            }
        };

        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        忘记密码
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                value={this.state.tel}
                                onChange={handleChange('tel')}
                                label="手机号"
                                autoComplete="off"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                label="邮箱"
                                value={this.state.email}
                                onChange={handleChange('email')}
                                autoComplete="off"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                value={this.state.newPassword}
                                label="密码"
                                onChange={handleChange('newPassword')}
                                type="password"
                                autoComplete="off"
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                variant="outlined"
                                required
                                fullWidth
                                value={this.state.repeatPassword}
                                label="确认密码"
                                onChange={handleChange('repeatPassword')}
                                type="password"
                                autoComplete="off"
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={handleSubmit}
                    >
                        重置
                    </Button>
                    <Grid container justify="flex-end">
                        <Grid item>
                            <Link href="/" variant="body2">
                                去登录
                            </Link>
                        </Grid>
                    </Grid>
                </div>
            </Container>
        )
    }
}

CustomerResetPwdPage.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(CustomerResetPwdPage);