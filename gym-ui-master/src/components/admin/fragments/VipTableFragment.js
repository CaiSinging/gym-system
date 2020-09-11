import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TablePagination from "@material-ui/core/TablePagination";
import React from "react";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core";
import TableHead from "@material-ui/core/TableHead";
import Breadcrumbs from "@material-ui/core/Breadcrumbs";
import RouterLink from "../../common/RouterLink";
import Link from "@material-ui/core/Link";
import Button from "@material-ui/core/Button";
import PropTypes from "prop-types";
import axiosInstance from "../axiosInstance";


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
    },
    title: {
        padding: theme.spacing(2),
    },
    table: {
        minWidth: 750,
    },
    tableWrapper: {
        overflowX: 'auto',
    },
}));

const headCells = [
    {id: 'gid', align: 'left', label: '编号'},
    {id: 'gname', align: 'left', label: '姓名'},
    {id: 'gender', align: 'left', label: '性别'},
    {id: 'tel', align: 'left', label: '手机号码'},
    {id: 'mail', align: 'left', label: '邮箱'},
    {id: 'operations', align: 'center', label: '操作'},
];


class VipTableFragment extends React.Component {

    constructor(props) {
        super(props);
        this.state = {guest: [], page: 0, rowsPerPage: 5};
    }

    componentDidMount() {
        axiosInstance.get('/guest/').then(res => {
            this.setState({guest: res.data.guests});
        });
    }

    render() {
        const {classes} = this.props;

        return (<div className={classes.root}>
            <Paper className={classes.breadcrumbPaper}>
                <Breadcrumbs aria-label="breadcrumb">
                    <Link color="inherit" component={RouterLink} to="/admin/">首页</Link>
                    <Typography color="textPrimary">查看客户</Typography>
                </Breadcrumbs>
            </Paper>
            <Paper className={classes.paper}>
                <Typography className={classes.title} variant="h6" id="tableTitle">
                    客户名单
                </Typography>
                <div className={classes.tableWrapper}>
                    <Table className={classes.table} aria-labelledby="tableTitle">
                        <TableHead>
                            <TableRow>
                                {headCells.map(headCell => (
                                    <TableCell key={headCell.id} align={headCell.align}>
                                        {headCell.label}
                                    </TableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.guest.slice(
                                this.state.page * this.state.rowsPerPage,
                                (this.state.page + 1) * this.state.rowsPerPage)
                                .map((row, index) => {
                                    return (
                                        <TableRow hover tabIndex={-1} key={index}>
                                            <TableCell component="th" scope="row">
                                                {row.id}
                                            </TableCell>
                                            <TableCell>{row.name}</TableCell>
                                            <TableCell>{row.gender}</TableCell>
                                            <TableCell>{row.tel}</TableCell>
                                            <TableCell>{row.email}</TableCell>
                                            <TableCell align="center">
                                                <Button component={RouterLink}
                                                        to={"/admin/changeService/" + row.id}>更改套餐</Button>
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}

                        </TableBody>
                    </Table>
                </div>
                <TablePagination component="div" page={this.state.page} count={this.state.guest.length}
                                 rowsPerPage={this.state.rowsPerPage} rowsPerPageOptions={[5, 10, 25, 50]}
                                 onChangePage={(event, newPage) => this.setState({page: newPage})}
                                 onChangeRowsPerPage={(event) => {
                                     this.setState({rowsPerPage: parseInt(event.target.value), page: 0});
                                 }}/>
            </Paper>
        </div>)
    }
}

VipTableFragment.propTypes = {
    classes: PropTypes.object.isRequired,
};
export default withStyles(styles)(VipTableFragment);