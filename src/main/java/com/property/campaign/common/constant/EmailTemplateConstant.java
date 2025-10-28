package com.property.campaign.common.constant;

public class EmailTemplateConstant {

    public static String EMAIL_TEMPLATE_PT=
            """
                    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                    <html xmlns="http://www.w3.org/1999/xhtml">
                                                                                                      
                    <head>
                    <meta charset="utf-8">
                    <meta name="x-apple-disable-message-reformatting">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta http-equiv="Content-Type" content="text/html charset=UTF-8" />
                    <title>*|MC:SUBJECT|*</title>
                    <style type="text/css">
                    @import url('https://fonts.googleapis.com/css2?family=Maven+Pro:wght@400;500;600&family=Roboto:ital,wght@0,300;0,400;0,500;0,700;1,300;1,400;1,500;1,700&display=swap');
                                                                                                      
                    @media only screen and (max-width: 480px) {
                    #bodyCell {
                    padding: 0 !important;
                    }
                                                                                                      
                                                                                                                  #templateContainer {
                                                                                                                      width: 100% !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .fluid {
                                                                                                                      width: 100% !important;
                                                                                                                      max-width: 100% !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .fluid-content {
                                                                                                                      width: 100% !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pr-0 {
                                                                                                                      padding-right: 0 !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pl-0 {
                                                                                                                      padding-left: 0 !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .px-0 {
                                                                                                                      padding-left: 0px !important;
                                                                                                                      padding-right: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-0 {
                                                                                                                      padding-bottom: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-15 {
                                                                                                                      padding-bottom: 15px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-10 {
                                                                                                                      padding-bottom: 10px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-30 {
                                                                                                                      padding-bottom: 30px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-20 {
                                                                                                                      padding-bottom: 20px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .none {
                                                                                                                      display: none !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .f-15 {
                                                                                                                      font-size: 15px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pr-0 {
                                                                                                                      padding-right: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .ml {
                                                                                                                      margin: 0 auto !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pb-0 {
                                                                                                                      padding-bottom: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .center {
                                                                                                                      text-align: center !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pt-0 {
                                                                                                                      padding-top: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pt-20 {
                                                                                                                      padding-top: 20px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .pt-0 {
                                                                                                                      padding-top: 0px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .mob_space {
                                                                                                                      display:block !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .sign_name {
                                                                                                                      font-size:18px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .sign_position {
                                                                                                                      font-size:12px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .elipse_mob {
                                                                                                                      max-width:105px !important;
                                                                                                                  }
                                                                                                      
                                                                                                                  .prof_pic_mobile {
                                                                                                                      max-width:65px !important;
                                                                                                                  }
                                                                                                                   .td-extra {
                                                                                                                      padding-top: 15px!important;
                                                                                                                  }
                                                                                                      
                                                                                                              }
                                                                                                              
                                                                                                             
                                                                                                              @media only screen and (max-width: 300px) {
                                                                                                
                                                                                                                          .elipse_mob {
                                                                                                                              max-width:100px !important;
                                                                                                                          }
                                                                                                              
                                                                                                                          .prof_pic_mobile {
                                                                                                                              max-width:60px !important;
                                                                                                                          }
                                                                                                                                           
                                                                                                                          .contact_size{
                                                                                                                           font-size:10px !important;
                                                                                                                        }
                                                                                                              }
                                                                                                          </style>
                                                                                                      </head>
                                                                                                      
                                                                                                      <body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0" bgcolor="#f1efef"
                                                                                                          style="font-family: 'Arial', sans-serif; padding: 0; margin: 0;" yahoo="fix">
                                                                                                          <center>
                                                                                                              <table align="center" border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" id="bodyTable"
                                                                                                                  style="background-color: #f1efef;">
                                                                                                                  <tr>
                                                                                                                      <td align="center" valign="top" id="bodyCell" style="padding-top: 20px; padding-bottom: 20px;">
                                                                                                                          <table border="0" cellpadding="0" cellspacing="0" width="600" id="templateContainer"
                                                                                                                              style="background-color: #fff;">
                                                                                                                              <tr>
                                                                                                                                  <td align="center" valign="top">
                                                                                                                                      <table border="0" cellpadding="0" cellspacing="0" width="100%" id="templateHeader">
                                                                                                                                          <tr>
                                                                                                                                              <td valign="top" align="left" class="headerContent">
                                                                                                                                                  <img src="https://media3.roomdb.io/BeL/zl3vuYMGpy4z4yO38DSUdc8l/Header.png" width="600"
                                                                                                                                                      style="max-width: 600px; max-height:40px; vertical-align: middle;" class="fluid" alt="">
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                          <tr>
                                                                                                                                              <td valign="top" align="center" class="pb-30"
                                                                                                                                                  style="padding-top: 40px; padding-bottom: 40px;">
                                                                                                                                                  <a href="#" target="_blank">
                                                                                                                                                      <img src="https://media3.roomdb.io/eht/4wdduRchCWOZSjrzi5P2oDCD/logo.png" width="244"
                                                                                                                                                          style="max-width: 244px; vertical-align: middle;" alt="">
                                                                                                                                                  </a>
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                      </table>
                                                                                                                                  </td>
                                                                                                                              </tr>
                                                                                                                              <!-- block 1 -->
                                                                                                                              <tr>
                                                                                                                                  <td align="left" valign="top" class="fluid px-20"
                                                                                                                                      style="padding-left: 40px; padding-right: 40px;">
                                                                                                                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                                      
                                                                                                                                          <tr>
                                                                                                                                              <td align="left" valign="top"
                                                                                                                                                  style="font-family: 'Arial', sans-serif; font-weight: 400; padding-bottom:16px; font-size: 16px; line-height: 24px; color: #505D68;">
                                                                                                                                                 %body_placeholder%
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                      
                                                                                                                                          <tr onclick="openLandingpage()" style="cursor:pointer;">
                                                                                                                                              <td align="left" valign="top" class="fluid"
                                                                                                                                                  style="background-color: #C72014; border-radius: 4px; padding-top: 15px; padding-bottom: 15px;">
                                                                                                                                                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                                                                                      <tr>
                                                                                                                                                          <td align="center" valign="top" class="f-15"
                                                                                                                                                              style="font-family: 'Arial', sans-serif; font-weight: 400; font-size: 16px; line-height: 22px; color: #fff;">
                                                                                                                                                              <a href="%permission%"
                                                                                                                                                                  target="_blank"
                                                                                                                                                                  style="text-decoration: none; color: #fff;">%p6%</a>
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                  </table>
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                          <tr>
                                                                                                                                              <td align="center" valign="top" class="pb-30"
                                                                                                                                                  style="font-family: 'Arial', sans-serif; font-weight: 400; font-size: 14px; line-height: 16px; color: #C72014; padding-top: 15px;">
                                                                                                                                                 %p7%
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                          <tr>
                                                                                                                                              <td align="left" valign="top" class="pt-0"
                                                                                                                                                  style="font-family: 'Arial', sans-serif; font-weight: 400; font-size: 16px; line-height: 18px; color: #505D68; padding-bottom: 10px; padding-top: 30px;">
                                                                                                                                                  %p8%
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                          <tr>
                                                                                                                                              <td align="left" valign="top" class="pb-20"
                                                                                                                                                  style="font-family: 'Arial', sans-serif; font-weight: 400; font-size: 16px; line-height: 18px; color: #505D68;">
                                                                                                                                                  %p9%
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                      </table>
                                                                                                                                  </td>
                                                                                                                              </tr>
                                                                                                                              <!-- block 1 -->
                                                                                                                              <tr>
                                                                                                                                  <td align="left" valign="top" class="td-extra" style="padding-top: 30px;">
                                                                                                                                      <table border="0" cellpadding="0" cellspacing="0" class="fluid" width="100%" id="templateHeader">
                                                                                                                                          <tr>
                                                                                                                                              <td valign="top" align="left" style="width:120px;">
                                                                                                                                              <img src="%signature_photo%" style="width:120px; max-width: 120px; vertical-align: middle;" alt="">
                                                                                                                                              </td>
                                                                                                                                              <td width="30" style="width:30px;">
                                                                                                                                              </td>
                                                                                                                                              <td align="left" valign="top" class="center_details">
                                                                                                                                                  <table border="0" cellspacing="0" cellpadding="0" width="100%" style="width:100%; max-width:254px;">
                                                                                                                                                      <tr>
                                                                                                                                                          <td class="sign_name" align="left" valign="top" style="font-family: 'Poppins', sans-serif;  font-size: 20px; font-weight: 500; line-height: 30px; color: #1E1E1E; white-space: nowrap;">
                                                                                                                                                              %signature_name%
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                      <tr>
                                                                                                                                                          <td class="sign_position" align="left" valign="top" style="font-family: 'Poppins', sans-serif;  font-size: 12px; font-weight: 400; line-height: 18px; color: #1E1E1E; padding-bottom: 15px;">
                                                                                                                                                              %signature_title%
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                      %numberbody%
                                                                                                                                                      <tr>
                                                                                                                                                          <td align="left" valign="top" style="padding-bottom: 5px;">
                                                                                                                                                              <table border="0" cellspacing="0" cellpadding="0">
                                                                                                                                                                  <tr>
                                                                                                                                                                      <td align="left" valign="middle" style="padding-right: 10px;">
                                                                                                                                                                          <a href="#" target="_blank">
                                                                                                                                                                              <img src="https://media3.roomdb.io/XVs/9gcLhcJw81ZrJk6BcQXkcEXM/mail.png" width="14" style="max-width: 14px; vertical-align: middle;" alt="">
                                                                                                                                                                          </a>
                                                                                                                                                                      </td>
                                                                                                                                                                      <td align="left" valign="middle" style="font-family: 'Poppins', sans-serif;  font-size: 12px; font-weight: 400; line-height: 18px; color: #1E1E1E; white-space: nowrap;">
                                                                                                                                                                          <a href="mailto:%signature_email_address%" class="contact_size" target="_blank" style="text-decoration: none; color: #1E1E1E;">%signature_email_address%</a>
                                                                                                                                                                      </td>
                                                                                                                                                                  </tr>
                                                                                                                                                              </table>
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                      %calendlybody%
                                                                                                                                                       <tr>
                                                                                                                                                          <td align="left" valign="top" style="padding-bottom: 5px;">
                                                                                                                                                              <table border="0" cellspacing="0" cellpadding="0">
                                                                                                                                                                  <tr>
                                                                                                                                                                   
                                                                                                                                                                      <td align="left" valign="middle" style="font-family: 'Poppins', sans-serif;  font-size: 12px; font-weight: 400; line-height: 20px; padding-bottom: 6px;">
                                                                                                                                                                          %companyLegal%
                                                                                                                                                                      </td>
                                                                                                                                                                  </tr>
                                                                                                                                                                  <tr>
                                                                                                                                                                      <td height="15" class="mob_space" style="display:none">
                                                                                                                                                                          <img alt="" style="display:block; border:0; max-height:21px;">
                                                                                                                                                                      </td>
                                                                                                                                                                  </tr>
                                                                                                                                                              </table>
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                 </table>
                                                                                                                                          </tr>
                                                                                                                                      </table>
                                                                                                                                  </td>
                                                                                                                              </tr>
                                                                                                                              <!-- footer part -->
                                                                                                                              <tr>
                                                                                                                                  <td align="left" valign="top" class="pb-0">
                                                                                                                                      <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                                                                          <tr>
                                                                                                                                              <td align="left" valign="top" class="fluid px-20"
                                                                                                                                                  style="padding-left: 40px; padding-right: 40px; padding-bottom: 50px; padding-top: 20px; background-color: #2E3F47; border-radius: 0px 0px 5px 5px;">
                                                                                                                                                  <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                                                                                      <tr>
                                                                                                                                                          <td align="left" valign="top"
                                                                                                                                                              style="font-family: 'Maven Pro', sans-serif; font-size: 12px; line-height: 16px; font-weight: 400; color: #fff;">
                                                                                                                                                             %unsubscribe_text%
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                  </table>
                                                                                                                                                   <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                                                                                      <tr>
                                                                                                                                                          <td align="left" valign="top"
                                                                                                                                                              style="font-family: 'Maven Pro', sans-serif; font-size: 12px; line-height: 16px; font-weight: 400; color: #303348;">
                                                                                                                                                             %property_id%
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                        <tr>
                                                                                                                                                          <td align="left" valign="top"
                                                                                                                                                              style="font-family: 'Maven Pro', sans-serif; font-size: 12px; line-height: 16px; font-weight: 400; color: #303348;">
                                                                                                                                                             %property_email%
                                                                                                                                                          </td>
                                                                                                                                                      </tr>
                                                                                                                                                  </table>
                                                                                                                                              </td>
                                                                                                                                          </tr>
                                                                                                                                      </table>
                                                                                                                                  </td>
                                                                                                                              </tr>
                                                                                                                              <!-- footer part -->
                                                                                                                          </table>
                                                                                                                      </td>
                                                                                                                  </tr>
                                                                                                              </table>
                                                                                                          </center>
                                                                                                         <script>
                                                                                                         
                                                                                                         function openLandingpage(){
                                                                                                         window.location = "%permission%";
                                                                                                         }
                                                                                                      </script>
                                                                                                      </body>
                                                                                                     </html>
                    """;

    public static String CULTSWITCH_EMAIL_TEMPLATE= """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                    <meta charset="utf-8">
                    <meta name="x-apple-disable-message-reformatting">
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta http-equiv="Content-Type" content="text/html charset=UTF-8" />
                    <title>*|MC:SUBJECT|*</title>
                    <style type="text/css">
                        @import url('https://fonts.googleapis.com/css2?family=Maven+Pro:wght@400;500;700&family=Raleway:ital,wght@0,400;0,500;0,600;0,700;0,800;1,400;1,500;1,600;1,700&family=Roboto:wght@300;400;500;700;900&display=swap');
                        @media only screen and (max-width: 480px){
                            #bodyCell{padding:0!important;}
                            #templateContainer{width:100%!important;}
                            .fluid{width: 100% !important; max-width:100%!important;}
                            .fluid-content{display:block!important; width:100%!important;}
                            .pr-0{padding-right:0!important;}
                            .pl-0{padding-left:0!important;}
                            .px-0{padding-left: 0px !important; padding-right: 0px !important;}
                            .px-20{padding-left: 20px !important; padding-right: 20px !important;}
                            .border-radius_0{border-radius: 0px 0px 0px 0px !important;}
                        }
                    </style>
                </head>
                <body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0" offset="0" bgcolor="#EEF7FD" style="font-family: 'Arial', sans-serif; padding: 0; margin: 0;"  yahoo="fix">
                    <center>
                        <table align="center" border="0" cellpadding="0" cellspacing="0" height="100%" width="100%" id="bodyTable" style="background-color: #EEF7FD;">
                            <tr>
                                <td align="center" valign="top" id="bodyCell" style="padding-top: 20px; padding-bottom: 20px;">
                                    <table border="0" cellpadding="0" cellspacing="0" width="600" id="templateContainer" style="background-color: #FFFFFF;">
                                        <tr>
                                            <td align="center" valign="top">
                                                <table border="0" cellpadding="0" cellspacing="0" width="100%" id="templateHeader">
                                                    <tr>
                                                        <td valign="top" align="left" width="100%" height="60" class="border-radius_0" style="background-color: #034CAF; border-radius: 10px 10px 0px 0px;">
          
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <!-- block 1 -->
                                        <tr>
                                            <td align="left" valign="top" class="fluid px-20" style="padding-left: 40px; padding-right: 40px; padding-top: 40px;">
                                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    <tr>
                                                        <td align="center" valign="top" style="padding-bottom: 30px;">
                                                            <a href="#" target="_blank">
                                                                <img src="https://media3.roomdb.io/ejY/uRj8o0r5zBH2HNaX4RbMmAmh/campaign_logo.png" width="222" style="max-width: 222px; vertical-align: middle;" alt="">
                                                            </a>
                                                        </td>
                                                    </tr>
            
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; line-height: 24px; font-weight: 400; color: #454753; padding-bottom: 20px;">
                                                         %body_placeholder%
                                                        </td>
                                                    </tr>
                                                  <tr onclick="openLandingpage()"  style="cursor:pointer;">
                                                  <td align="center" valign="top" class="px-20" style="font-family: 'Roboto', sans-serif; font-size: 16px; line-height: 24px; font-weight: 400; color: #ffffff; background: #034CAF; padding: 10px 30px; border-radius: 40px;">
                                                  <a href="%permission%" target="_blank" style="text-decoration: none; color: #ffffff;">%p6%</a>
                                                  </td>
                                                  </tr>
                                                  <tr>
                                                  <td align="center" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 14px; line-height: 16.41px; font-weight: 400; color: #034CAF; padding-top: 15px; padding-bottom: 30px;">
                                                  %p7%
                                                  </td>
                                                  </tr>
                                                  <tr>
                                                  <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; line-height: 18.75px; font-weight: 400; color: #454753; padding-bottom: 10px;">
                                                   %p8%
                                                   </td>
                                                   </tr>
                                                  <tr>
                                                  <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; line-height: 18.75px; font-weight: 400; color: #454753; padding-bottom: 20px;">
                                                   %p9%
                                                   </td>
                                                   </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <!-- block 1 -->
            
                                        <!-- block 2 -->
                                        <tr>
                                            <td align="left" valign="top" class="fluid px-20" style="padding-left: 40px; padding-right: 40px; padding-bottom: 40px;">
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td align="left" valign="top" style="padding-bottom: 10px;">
                                                            <table border="0" cellspacing="0" cellpadding="0">
                                                                <tr>
                                                                    <td align="left" valign="middle" width="100" style="padding-right: 16px;">
                                                                        <img src="%signature_photo%" width="100" style="max-width: 100px; vertical-align: middle;" alt="">
                                                                    </td>
                                                                    <td align="left" valign="middle" class="fluid">
                                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                            <tr>
                                                                                <td align="left" valign="top" style="font-family: 'Raleway', sans-serif; font-size: 20px; font-weight: 700; line-height: 30px; color: #454753; padding-bottom: 4px;">
                                                                                   %signature_name%
                                                                                </td>
                                                                            </tr>
                                                                            <tr>
                                                                                <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; font-weight: 400; line-height: 20px; color: #454753; font-style: italic;">
                                                                                    %signature_title%
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left" valign="top" class="fluid pl-0" style="padding-left: 126px;">
                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    %numberbody%
                                                              <tr>
                                                                    <td align="left" valign="top" style="padding-bottom: 16px;">
                                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                            <tr>
                                                                                <td align="left" valign="middle" width="15" style="padding-right: 10px;">
                                                                                    <img src="https://media3.roomdb.io/NOW/ntnYvPfEd9alpNr0144ImvHe/campaign_email.png" width="14" style="max-width: 14px; vertical-align: middle;" alt="">
                                                                                </td>
                                                                                <td align="left" valign="middle">
                                                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                        <tr>
                                                                                            <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; font-weight: 400; line-height: 20px; color: #454753;">
                                                                                               <a href="mailto:%signature_email_address%" target="_blank" style="text-decoration: none; color: #454753;">%signature_email_address%</a>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                                 %calendlybody%
                                                               <tr>
                                                                    <td align="left" valign="top">
                                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                            <tr>
                                                                                <td align="left" valign="top" width="15" style="padding-right: 10px;">
                                                                                    <img src="https://media3.roomdb.io/atp/sZbXNv4jteAGEA6M3r0v3u7l/campaign_location.png" width="11" style="max-width: 11px; vertical-align: middle;" alt="">
                                                                                </td>
                                                                                <td align="left" valign="top">
                                                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                        <tr>
                                                                                            <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; font-weight: 400; line-height: 20px; color: #454753; ">
                                                                                               %companyLegal%
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <!-- block 2 -->
                        
                                        <!-- footer  -->
                                        <tr>
                                            <td align="left" valign="top" class="fluid px-20 border-radius_0" style="padding-left: 40px; padding-right: 40px; padding-top: 20px; padding-bottom: 50px; background-color:#303348; border-radius: 0px 0px 10px 10px;">
                                                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 12px; font-weight: 400; line-height: 16px; color: #FFFFFF;">
                                                           %unsubscribe_text%
                                                      </td>
                                                    </tr>
                                                </table>
                                                
                                                 <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 12px; font-weight: 400; line-height: 16px; color: #303348;">
                                                        %property_id%
                                                      </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 12px; font-weight: 400; line-height: 16px; color: #303348;">
                                                        %property_email%
                                                      </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <!-- footer  -->
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </center>
                     <script>
                       function openLandingpage(){
                        window.location = "%permission%";
                       }
                 </script>
                </body>
            </html>
            """;

    public static String CULTSWITCH_CALENDLY =  """
    <tr> <td align="left" valign="top" style="padding-bottom: 16px;">
                                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                            <tr>
                                                                                <td align="left" valign="middle" width="15" style="padding-right: 10px;">
                                                                                    <img src="https://media3.roomdb.io/eMX/d70yk7hNGglzyudWoGswL1tQ/campaign_calendly.png" width="14" style="max-width: 14px; vertical-align: middle;" alt="">
                                                                                </td>
                                                                                <td align="left" valign="middle">
                                                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                        <tr>
                                                                                            <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; font-weight: 400; line-height: 20px; color: #454753;">
                                                                                               <a href="%calendly%" target="_blank" style="text-decoration: none; color: #454753;">%Signature_meeting%</a>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
    """;


    public static String MM_CALENDLY =
            """
                            <tr> <td align="left" valign="top" style="padding-bottom: 5px;">
                            <table border="0" cellspacing="0" cellpadding="0">
                            <tr><td align="left" valign="middle" style="padding-right: 10px;">
                            <a href="#" target="_blank">
                            <img src="https://media3.roomdb.io/ge8/WlT3qjkjniIg7mPAbqOE3S8Q/book.png" width="14" style="max-width: 14px; vertical-align: middle;" alt="">
                            </a>
                            </td>
                            <td align="left" valign="middle" style="font-family: 'Poppins', sans-serif;  font-size: 12px; font-weight: 400; line-height: 18px; color: #1E1E1E;">
                            <a href="%calendly%" class="contact_size" target="_blank" style="text-decoration: none; color: #1E1E1E; white-space: nowrap;">%Signature_meeting%</a>
                            </td>
                            </tr>
                            </table>
                            </td>
                            </tr>
    """;

    public static String CULTSWITCH_NUMBER =  """
                                                                <tr>
                                                                    <td align="left" valign="top" style="padding-bottom: 16px;">
                                                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                            <tr>
                                                                                <td align="left" valign="top" width="15" style="padding-right: 10px;">
                                                                                    <img src="https://media3.roomdb.io/qfu/s6TELa1zu9HQjGOp7O02Zpov/campaign_phone.png" width="14" style="max-width: 14px; vertical-align: middle;" alt="">
                                                                                </td>
                                                                                <td align="left" valign="top" class="fluid">
                                                                                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                                                        <tr>
                                                                                            <td align="left" valign="top" style="font-family: 'Roboto', sans-serif; font-size: 16px; font-weight: 400; line-height: 20px; color: #454753;">
                                                                                                Tel: <a href="tel:%signature_telephone%" target="_blank" style="text-decoration: none; color: #454753;">%signature_telephone%</a>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </td>
                                                                            </tr>
                                                                        </table>
                                                                    </td>
                                                                </tr>
    """;


    public static String MM_NUMBER =
            """
               <tr>
               <td align="left" valign="top" style="padding-bottom: 5px;">
               <table border="0" cellspacing="0" cellpadding="0">
               <tr> <td align="left" valign="middle" style="padding-right: 10px;">
               <a href="#" target="_blank">
               <img src="https://media3.roomdb.io/LlU/Vbgj8zYJ5hLVXyimpFNFqA74/phone.png" width="12" style="max-width: 12px; vertical-align: middle;" alt="">
               </a></td>
               <td align="left" valign="middle" style="font-family: 'Poppins', sans-serif;  font-size: 12px; font-weight: 400; white-space: nowrap; line-height: 18px; color: #1E1E1E;">
               <a href="tel:%signature_telephone%" class="contact_size" target="_blank" style="text-decoration: none; color: #1E1E1E;">%signature_telephone%</a>
               </td>
               </tr>
               </table>
               </td>
               </tr>
    """;


    public static String DAILY_CAMPAIGN_EMAIL_TEMPLATE =
                """
                Campaign_id / Campaign_name / signature_name  <br>
                Period: %100daysDate% - %currentDate% <br><br>
           
                 Number of sent campaign emails:  %total% = 100,0% <br>
                 Email status<br>
                  <table>
                 <thead>
                 <th valign="middle" style="min-width: 84px;">Status</th>
                 <th valign="middle">Total</th>
                 <th valign="middle">Percentage</th>
                </thead>
                <tbody>
                 <tr><td valign="middle">sent</td>%sentT%</tr>
                 <tr><td valign="middle">delivered</td> %deliveredT% </tr>
                 <tr><td valign="middle">opened</td> %openedT%</tr>
                 <tr><td valign="middle">clicked</td> %clickedT% </tr>
                 <tr><td valign="middle">delay</td> %delayT% </tr>
                 <tr><td valign="middle">rejected</td> %rejectedT% </tr>
                 <tr><td valign="middle">failed</td> %failedT% </tr>
                </tbody>
                </table>
                 <br>
                 Followed up emails,  %follow_total% = %follow_perct%<br>
                 Followed up status<br>
                 <table>
                <thead>
                <th valign="middle" style="min-width: 84px;">Status</th>
                <th valign="middle">Total</th>
                <th valign="middle">Percentage</th>
                </thead>
                <tbody>
                 <tr><td valign="middle"> discussion</td> %discussion_total% </tr>
                 <tr><td valign="middle" >interested</td> %interested_total%</tr>
                 <tr><td valign="middle">onboarding</td>%onboarding_total%</tr>
                 <tr><td valign="middle" >live</td>%live_total%</tr>
                 <tr> <td valign="middle" >not interested</td>%notinterested_total%</tr>
                </tbody>
                </table>
             
              """;

}
