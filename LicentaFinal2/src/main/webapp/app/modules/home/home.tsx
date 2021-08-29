import './home.scss';

import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps} from 'react-router-dom';
import { Button, Col, Row, Table, Input, Form } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntitiesHome, reset, getAllPostsFilter } from 'app/entities/post/post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT,category,judet } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { faBold, faCheck } from '@fortawesome/free-solid-svg-icons';
import cityReducer from 'app/entities/city/city.reducer';
import { Tooltip } from '@material-ui/core'

export const Home = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const [judetTemp, setJudetTemp] = useState('');
  const [cityTemp,setCityTemp] = useState('');
  const [categoryTemp, setCategoryTemp] = useState('');
  const [subcategoryTemp,setSubcategoryTemp] = useState('');


  const [sorting, setSorting] = useState(false);

  const postList = useAppSelector(state => state.post.entities);
  const loading = useAppSelector(state => state.post.loading);
  const totalItems = useAppSelector(state => state.post.totalItems);
  const links = useAppSelector(state => state.post.links);
  const entity = useAppSelector(state => state.post.entity);
  const updateSuccess = useAppSelector(state => state.post.updateSuccess);
  const account = useAppSelector(state => state.authentication.account);

  const disable = useAppSelector(state=> state.authentication.account.infoCompleted || false)

  const getAllEntities = () => {
    if(!(judetTemp != ""  && cityTemp != "" && categoryTemp != "" && subcategoryTemp != "")) {
    dispatch(
      getEntitiesHome({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
    } else {
      dispatch(getAllPostsFilter({page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
        judet: judetTemp,
        city: cityTemp,
        category: categoryTemp,
        subcategory: subcategoryTemp
      }));
    }
  };


  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };


  const handleChange = (e) => {
    setJudetTemp(e.target.value);
  }

  const handleChangeCity = (e) => {
    setCityTemp(e.target.value);
  }

  const handleChangeCategory = (e) => {
    console.log(e.target.value);
    setCategoryTemp(e.target.value);
  }

  const handleChangeSubcat = (e) => {
    console.log(e.target.value);
    setSubcategoryTemp(e.target.value);
    console.log(subcategoryTemp);
  }

  const searchPosts = (e) => {
    e.preventDefault();
    dispatch(getAllPostsFilter({page: paginationState.activePage - 1,
      size: paginationState.itemsPerPage,
      sort: `${paginationState.sort},${paginationState.order}`,
      judet: judetTemp,
      city: cityTemp,
      category: categoryTemp,
      subcategory: subcategoryTemp
    }));
    resetAll();

  }

  const redirectToAddPost =(e) => {
    props.history.push("/post/new");
  }

  const { match } = props;

  return (
    <div>
      <h2 id="post-heading" data-cy="PostHeading">
        Posts
      </h2>
      <Form onSubmit={searchPosts}>
        
      <div style={{display:'flex', alignContent:'center'}}>
        <Input style={{width:"40%", marginRight:"50px",backgroundImage:'linear-gradient(to right, LightSkyBlue,Lavender)'}} type="select" name="category" id="category" onChange={handleChangeCategory} required>
            <option key="0-jud" value="">Choose Category</option>
            {category.map((cat, index) => {
              return <option key={cat.id}>{cat.name}</option>
            })}
        </Input>
        <Input style={{width:"40%",marginRight:"50px", backgroundImage:'linear-gradient(to right, Lavender,LightPink)'}} type="select" name="category-subcategory" id="category-subcategory" onChange={handleChangeSubcat} required>
            <option key="0=subcategory" value="">Choose Subcategory</option>
            {category.map((cat, index) => {
                if(cat.name == categoryTemp){
                    return cat.subcategory.map((subcat, index2) => {
                        return <option key={subcat.id}>{subcat.name}</option>
                    })
                  }
            })}
        </Input>
        </div>
        <div style={{display:'flex', alignContent:'center'}}>
       <Input style={{width:"40%", marginRight:"50px",backgroundImage:'linear-gradient(to right, LightSkyBlue,Lavender)'}} type="select" name="judet" id="judet" onChange={handleChange} required>
            <option key="0-jud" value="">Choose Judet</option>
            {judet.map((judet1, index) => {
              return <option key={judet1.id}>{judet1.name}</option>
            })}
        </Input>
        
        <Input style={{width:"40%",marginRight:"50px", backgroundImage:'linear-gradient(to right, Lavender,LightPink)'}} type="select" name="judet-city" id="judet-city" onChange={handleChangeCity} required>
            <option key="0-category" value="">Choose City</option>
            {judet.map((judet1, index) => {
                if(judet1.name == judetTemp){
                    return judet1.city.map((city, index2) => {
                        return <option key={city.id}>{city.name}</option>
                    })
                  }
            })}
        </Input>
        <Button style={{backgroundColor:"LightPink", color:'black'}} type='submit' >Search</Button></div>
        </Form>
        <div style={{width:"100%", alignItems: 'center'}}>
        <table width="100%">
          <tr style={{height:"15px"}}></tr>
          <tr>
          <td> </td>
          <td> </td>
          <Tooltip title="Login or complete your account details to add posts" arrow disableTouchListener>
        <td style={{width:"20%", alignContent: 'end', border:"2px", borderColor:"black"}}><Button style={{backgroundColor:"CornflowerBlue", color:'black', border:"2px", borderColor:"black"}} type='submit' block disabled = {!disable} id="button" onClick={redirectToAddPost}>Adauga anunt</Button></td></Tooltip>
        <td> </td>
        <td> </td>
        </tr>
        </table>
        </div>
      <div className="table-responsive">
        
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {postList && postList.length > 0 ? (
            <Table responsive style={{ borderCollapse:'separate',
              borderSpacing:"0 15px"}}>
              <tbody>
                {postList.map((post, i) => (
                  <tr style={{backgroundImage:'linear-gradient(to right, LightSkyBlue,LightPink)'}} key={`entity-${i}`} data-cy="entityTable">
                   <td >
                     <p style={{color:'DimGrey'}}><b>{post.title}</b></p> <p>{post.description}</p>
                    {post.created_at ? <TextFormat type="date" value={post.created_at} format={APP_DATE_FORMAT}/> : null}                      
                    
                    </td>
                    <td style={{textAlign:'end', verticalAlign:'bottom'}}>{post.category} - {post.subcategory}</td>
                    <td style={{textAlign:'end', verticalAlign:'bottom'}}> {post.judet} - {post.city} </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Posts found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Home;
