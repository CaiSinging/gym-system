import React from "react";
import RouterLink from "../../common/RouterLink";
import {
    Breadcrumbs,
    Button,
    FormControl,
    InputLabel,
    Link,
    Paper,
    Select,
    Typography,
    withStyles
} from "@material-ui/core";
import {KeyboardDatePicker,} from '@material-ui/pickers';
import axiosInstance from "../axiosInstance";
import moment from "moment";

const styles = (theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
    },
    breadcrumbPaper: {
        marginBottom: theme.spacing(2),
        padding: theme.spacing(2),
    },
    paper: {
        width: '100%',
        marginBottom: theme.spacing(2),
        paddingBottom: theme.spacing(2),
    },
    title: {
        padding: theme.spacing(2),
    },

    visuallyHidden: {
        border: 0,
        clip: 'rect(0 0 0 0)',
        height: 1,
        margin: -1,
        overflow: 'hidden',
        padding: 0,
        position: 'absolute',
        top: 20,
        width: 1,
    },
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formInput: {
        width: '100%',
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        marginTop: theme.spacing(1),
        marginBottom: theme.spacing(1),
        width: 200,
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

class ServiceChangeFragment extends React.Component {
    constructor(props) {
        super(props);
        this.state = {vip: false, vreg: new Date(), vfin: new Date()};
    }

    componentDidMount() {
        const params = this.props.match.params;
        axiosInstance.get('/guest/' + params.id + '/admin').then((res) => {
            this.setState({
                vip: res.data.vip,
                vreg: res.data.vreg,
                vfin: res.data.vfin,
            })
        })
    }

    render() {
        const {classes, history} = this.props;
        const params = this.props.match.params;
        const self = this;
        const handleStartDateChange = date => {
            self.setState({vreg: date});
        };
        const handleEndDateChange = date => {
            self.setState({vfin: date});
        };
        const handleChange = name => event => {
            self.setState({[name]: event.target.value,});
        };
        const handleSubmit = () => {
            axiosInstance.post('/guest/' + params.id + '/service', {
                vip: this.state.vip,
                vreg: moment(this.state.vreg).format("YYYY/MM/DD"),
                vfin: moment(this.state.vfin).format("YYYY/MM/DD"),
            }).then(() => {
                history.push('/admin/vip');
            }).catch((error) => {
                if (error.response)
                    alert(error.response.data.error || error.response.data.message);
            });
        };
        return (
            <div>
                <Paper className={classes.breadcrumbPaper}>
                    <Breadcrumbs aria-label="breadcrumb">
                        <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                        <Link color="inherit" component={RouterLink} to="/admin/vip">查看会员</Link>
                        <Typography color="textPrimary">更改套餐</Typography>
                    </Breadcrumbs>
                </Paper>
                <Paper className={classes.paper}>
                    <Typography className={classes.title} variant="h6" id="tableTitle">
                        更改套餐
                    </Typography>
                    <div>
                        <form noValidate autoComplete="off">
                            <div className={classes.formInput}>
                                <FormControl className={classes.formControl}>
                                    <InputLabel>是否为会员</InputLabel>
                                    <Select value={this.state.vip} onChange={handleChange('vip')}>
                                        <option value={true}>是</option>
                                        <option value={false}>否</option>
                                    </Select>
                                </FormControl>
                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD"
                                                    margin="normal" label="开始日期" value={this.state.vreg}
                                                    onChange={handleStartDateChange}/>
                            </div>
                            <div className={classes.formInput}>
                                <KeyboardDatePicker disableToolbar variant="inline" format="YYYY/MM/DD"
                                                    margin="normal" label="到期日期" value={this.state.vfin}
                                                    onChange={handleEndDateChange}/>
                            </div>
                            <div className={classes.formInput}>
                                <Button id="button" variant="outlined" onClick={handleSubmit}>确认</Button>
                            </div>
                        </form>
                    </div>
                </Paper>
            </div>
        );
    }
}

export default withStyles(styles)(ServiceChangeFragment)